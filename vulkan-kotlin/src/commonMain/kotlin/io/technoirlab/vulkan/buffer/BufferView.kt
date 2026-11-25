package io.technoirlab.vulkan.buffer

import io.technoirlab.volk.VK_OBJECT_TYPE_BUFFER_VIEW
import io.technoirlab.volk.VkBufferView
import io.technoirlab.volk.VkObjectType
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.device.Device
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkBufferView].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkBufferView.html">VkBufferView Manual Page</a>
 */
class BufferView internal constructor(
    private val device: Device,
    override val handle: VkBufferView,
) : VulkanObject,
    AutoCloseable {
    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_BUFFER_VIEW

    /**
     * Destroy the buffer view.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyBufferView.html">vkDestroyBufferView Manual Page</a>
     */
    override fun close() {
        device.dispatch.vkDestroyBufferView!!(device.handle, handle, null)
    }
}
