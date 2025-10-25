package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_SHADER_MODULE
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkShaderModule
import io.technoirlab.volk.vkDestroyShaderModule
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkShaderModule].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkShaderModule.html">VkShaderModule Manual Page</a>
 */
class ShaderModule internal constructor(
    private val device: VkDevice,
    override val handle: VkShaderModule
) : Object<VkShaderModule> {

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
