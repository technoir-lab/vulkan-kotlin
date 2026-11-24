package io.technoirlab.vulkan.shader

import io.technoirlab.volk.VK_OBJECT_TYPE_SHADER_MODULE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkShaderModule
import io.technoirlab.volk.VkShaderModuleCreateInfo
import io.technoirlab.volk.vkDestroyShaderModule
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.memory.MemoryRegion
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkShaderModule].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkShaderModule.html">VkShaderModule Manual Page</a>
 */
class ShaderModule internal constructor(
    private val device: VkDevice,
    override val handle: VkShaderModule,
) : VulkanObject,
    AutoCloseable {
    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_SHADER_MODULE

    /**
     * Destroy the shader module.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyShaderModule.html">vkDestroyShaderModule Manual Page</a>
     */
    override fun close() {
        vkDestroyShaderModule!!(device, handle, null)
    }
}

/**
 * Describe SPIR-V shader code for a pipeline shader stage or a shader module.
 *
 * Chain the returned structure into the `pNext` of a `VkPipelineShaderStageCreateInfo` whose `module` is left
 * null to create a pipeline without a shader module object. This requires the `maintenance5` feature, which every
 * Vulkan 1.4 device supports, to be enabled on the device.
 *
 * [code] must be a complete SPIR-V module stored as 32-bit words in host byte order, which on every supported
 * target is the layout of a `.spv` file. Assertions check that its size is greater than zero and a multiple of 4,
 * and that its first word is the SPIR-V magic number. They do not validate the SPIR-V.
 *
 * [code] may start at any address. Vulkan requires SPIR-V code to be aligned to 4 bytes, so code that is not
 * aligned is copied into memory allocated from [allocator], and aligned code is referenced without a copy.
 *
 * The returned structure is allocated from [allocator] and may point into [code], so it is valid only while
 * both are.
 *
 * @param code The memory region holding the SPIR-V code.
 * @return The structure describing [code], with no `pNext` chain of its own.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkShaderModuleCreateInfo.html">VkShaderModuleCreateInfo Manual Page</a>
 */
context(allocator: NativePlacement)
fun shaderModuleCreateInfo(code: MemoryRegion): VkShaderModuleCreateInfo = allocator.alloc {
    sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
    codeSize = code.size
    pCode = allocator.alignedSpirvWords(code)
}
