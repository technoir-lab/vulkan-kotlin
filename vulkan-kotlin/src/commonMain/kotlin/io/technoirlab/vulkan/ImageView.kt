package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_IMAGE_VIEW
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkImageView
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkDestroyImageView
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkImageView].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkImageView.html">VkImageView Manual Page</a>
 */
class ImageView internal constructor(
    private val device: VkDevice,
    override val handle: VkImageView
) : Object<VkImageView> {

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
        vkDestroyImageView!!(device, handle, null)
    }
}
