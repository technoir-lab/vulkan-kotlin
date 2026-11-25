package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_OBJECT_TYPE_IMAGE_VIEW
import io.technoirlab.volk.VkImageView
import io.technoirlab.volk.VkObjectType
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.device.Device
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkImageView].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkImageView.html">VkImageView Manual Page</a>
 */
class ImageView internal constructor(
    private val device: Device,
    override val handle: VkImageView,
) : VulkanObject,
    AutoCloseable {
    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_IMAGE_VIEW

    /**
     * Destroy the image view.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyImageView.html">vkDestroyImageView Manual Page</a>
     */
    override fun close() {
        device.dispatch.vkDestroyImageView!!(device.handle, handle, null)
    }
}
