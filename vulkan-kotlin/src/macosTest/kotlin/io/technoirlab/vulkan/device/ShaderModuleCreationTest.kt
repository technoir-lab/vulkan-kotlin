package io.technoirlab.vulkan.device

import io.technoirlab.volk.VK_SHADER_STAGE_COMPUTE_BIT
import io.technoirlab.vulkan.Vulkan
import io.technoirlab.vulkan.memory.MemoryRegion
import io.technoirlab.vulkan.memory.useMemoryRegion
import io.technoirlab.vulkan.shader.ShaderModule
import io.technoirlab.vulkan.shader.allocSpirvCode
import io.technoirlab.vulkan.shader.spirvBytes
import io.technoirlab.vulkan.withTestDevice
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ShaderModuleCreationTest {
    private val vulkan = Vulkan()

    @AfterTest
    fun tearDown() {
        vulkan.close()
    }

    @Test
    fun `createShaderModule creates a usable module from aligned code`() = withCode(misalignment = 0) { device, code ->
        val shaderModule = device.createShaderModule(code)

        shaderModule.use { assertUsableInComputePipeline(device, it) }
    }

    @Test
    fun `createShaderModule creates a usable module from misaligned code`() = withCode(misalignment = 1) { device, code ->
        val shaderModule = device.createShaderModule(code)

        shaderModule.use { assertUsableInComputePipeline(device, it) }
    }

    @Test
    fun `createShaderModule creates a usable module from a pinned byte array`() = vulkan.withTestDevice { _, device ->
        val bytes = spirvBytes(NOOP_COMPUTE_SHADER)

        val shaderModule = bytes.useMemoryRegion { device.createShaderModule(it) }

        shaderModule.use { assertUsableInComputePipeline(device, it) }
    }

    private fun assertUsableInComputePipeline(device: Device, shaderModule: ShaderModule): Unit = memScoped {
        val entryPoint = "main".cstr.ptr
        device.createPipelineLayout().use { layout ->
            val pipeline = device.createComputePipeline(
                layout,
                shaderStage = {
                    stage = VK_SHADER_STAGE_COMPUTE_BIT
                    module = shaderModule.handle
                    pName = entryPoint
                },
            )

            assertNotNull(pipeline).close()
        }
    }

    private fun withCode(misalignment: Int, block: (Device, MemoryRegion) -> Unit) {
        vulkan.withTestDevice { _, device ->
            memScoped {
                block(device, allocSpirvCode(NOOP_COMPUTE_SHADER, misalignment))
            }
        }
    }
}
