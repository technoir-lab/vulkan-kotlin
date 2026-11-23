package io.technoirlab.vulkan.device

import io.technoirlab.volk.VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT
import io.technoirlab.volk.VK_SHADER_STAGE_COMPUTE_BIT
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkPipelineCreateFlags2
import io.technoirlab.volk.VkPipelineShaderStageCreateInfo
import io.technoirlab.vulkan.Vulkan
import io.technoirlab.vulkan.pipeline.Pipeline
import io.technoirlab.vulkan.pipeline.PipelineCreationResult
import io.technoirlab.vulkan.withTestDevice
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
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
    private val computeShader = device.createShaderModule(COMPUTE_SHADER)

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

    private companion object {
        // Assembled with `spirv-as --target-env vulkan1.0` from:
        //                OpCapability Shader
        //                OpMemoryModel Logical GLSL450
        //                OpEntryPoint GLCompute %main "main"
        //                OpExecutionMode %main LocalSize 1 1 1
        //        %void = OpTypeVoid
        //    %function = OpTypeFunction %void
        //        %main = OpFunction %void None %function
        //       %entry = OpLabel
        //                OpReturn
        //                OpFunctionEnd
        private val COMPUTE_SHADER = listOf(
            0x07230203u, 0x00010000u, 0x00070000u, 0x00000005u, 0x00000000u, 0x00020011u, 0x00000001u, 0x0003000eu,
            0x00000000u, 0x00000001u, 0x0005000fu, 0x00000005u, 0x00000001u, 0x6e69616du, 0x00000000u, 0x00060010u,
            0x00000001u, 0x00000011u, 0x00000001u, 0x00000001u, 0x00000001u, 0x00020013u, 0x00000002u, 0x00030021u,
            0x00000003u, 0x00000002u, 0x00050036u, 0x00000002u, 0x00000001u, 0x00000000u, 0x00000003u, 0x000200f8u,
            0x00000004u, 0x000100fdu, 0x00010038u,
        )
    }
}
