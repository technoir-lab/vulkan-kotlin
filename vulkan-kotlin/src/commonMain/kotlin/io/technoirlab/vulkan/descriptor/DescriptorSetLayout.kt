package io.technoirlab.vulkan.descriptor

import io.technoirlab.volk.VK_OBJECT_TYPE_DESCRIPTOR_SET_LAYOUT
import io.technoirlab.volk.VkDescriptorSetLayout
import io.technoirlab.volk.VkObjectType
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.device.Device
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkDescriptorSetLayout].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDescriptorSetLayout.html">VkDescriptorSetLayout Manual Page</a>
 */
class DescriptorSetLayout internal constructor(
    private val device: Device,
    override val handle: VkDescriptorSetLayout,
) : VulkanObject,
    AutoCloseable {
    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_DESCRIPTOR_SET_LAYOUT

    /**
     * Destroy the descriptor set layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDescriptorSetLayout.html">vkDestroyDescriptorSetLayout Manual Page</a>
     */
    override fun close() {
        device.dispatch.vkDestroyDescriptorSetLayout!!(device.handle, handle, null)
    }
}
