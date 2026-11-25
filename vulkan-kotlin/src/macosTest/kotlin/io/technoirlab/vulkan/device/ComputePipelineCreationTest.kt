package io.technoirlab.vulkan.device

import io.technoirlab.volk.VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT
import io.technoirlab.volk.VK_SHADER_STAGE_COMPUTE_BIT
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkPipelineCreateFlags2
import io.technoirlab.volk.VkPipelineShaderStageCreateInfo
import io.technoirlab.vulkan.Vulkan
import io.technoirlab.vulkan.pipeline.Pipeline
import io.technoirlab.vulkan.pipeline.PipelineCreationResult
import io.technoirlab.vulkan.shader.allocSpirvCode
import io.technoirlab.vulkan.shader.shaderModuleCreateInfo
import io.technoirlab.vulkan.withTestDevice
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ComputePipelineCreationTest {
    private val vulkan = Vulkan()

    @AfterTest
    fun tearDown() {
        vulkan.close()
    }

    @Test
    fun `createComputePipeline creates a pipeline`() = withPipelineFixture { fixture ->
        val pipeline = fixture.createPipeline()

        assertNotNull(pipeline).close()
    }

    @Test
    fun `createComputePipeline accepts failing on a required compile`() = withPipelineFixture { fixture ->
        val pipeline = fixture.createPipeline(VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT)

        pipeline?.close()
    }

    @Test
    fun `createComputePipelineWithFeedback reports feedback for the compute stage`() = withPipelineFixture { fixture ->
        val result = fixture.createPipelineWithFeedback()

        assertNotNull(result.pipeline).close()
        assertEquals(1, result.pipelineStageCreationFeedbacks.size)
    }

    @Test
    fun `createComputePipelineWithFeedback reports feedback when failing on a required compile`() = withPipelineFixture { fixture ->
        val result = fixture.createPipelineWithFeedback(VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT)

        result.pipeline?.close()
        assertEquals(1, result.pipelineStageCreationFeedbacks.size)
    }

    @Test
    fun `createComputePipeline creates a pipeline from shader code chained into the stage`() {
        vulkan.withTestDevice { _, device ->
            memScoped {
                val entryPoint = "main".cstr.ptr
                val code = shaderModuleCreateInfo(allocSpirvCode(NOOP_COMPUTE_SHADER, misalignment = 1))
                device.createPipelineLayout().use { layout ->
                    val pipeline = device.createComputePipeline(
                        layout,
                        shaderStage = {
                            stage = VK_SHADER_STAGE_COMPUTE_BIT
                            pNext = code.ptr
                            pName = entryPoint
                        },
                    )

                    assertNotNull(pipeline).close()
                }
            }
        }
    }

    private fun withPipelineFixture(block: (ComputePipelineFixture) -> Unit) {
        vulkan.withTestDevice(features13 = { pipelineCreationCacheControl = VK_TRUE }) { _, device ->
            memScoped {
                ComputePipelineFixture(device, scope = this).use(block)
            }
        }
    }
}

/**
 * The inputs of a minimal compute pipeline whose shader does nothing.
 *
 * Native memory referenced by the shader stage is allocated in [scope], which must outlive the fixture.
 */
private class ComputePipelineFixture(
    private val device: Device,
    scope: MemScope,
) : AutoCloseable {
    private val entryPoint = "main".cstr.getPointer(scope)
    private val layout = device.createPipelineLayout()
    private val computeShader = device.createShaderModule(NOOP_COMPUTE_SHADER)

    private val shaderStage: VkPipelineShaderStageCreateInfo.() -> Unit = {
        stage = VK_SHADER_STAGE_COMPUTE_BIT
        module = computeShader.handle
        pName = entryPoint
    }

    fun createPipeline(flags: VkPipelineCreateFlags2 = 0uL): Pipeline? = device.createComputePipeline(layout, shaderStage, flags)

    fun createPipelineWithFeedback(flags: VkPipelineCreateFlags2 = 0uL): PipelineCreationResult =
        device.createComputePipelineWithFeedback(layout, shaderStage, flags)

    override fun close() {
        computeShader.close()
        layout.close()
    }
}
