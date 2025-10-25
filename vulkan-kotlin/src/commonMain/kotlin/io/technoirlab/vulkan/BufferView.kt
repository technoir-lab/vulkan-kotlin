package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_BUFFER_VIEW
import io.technoirlab.volk.VkBufferView
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkDestroyBufferView
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkBufferView].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkBufferView.html">VkBufferView Manual Page</a>
 */
class BufferView internal constructor(
    private val device: VkDevice,
    override val handle: VkBufferView
) : Object<VkBufferView> {

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
        vkDestroyBufferView!!(device, handle, null)
    }
}
