package io.technoirlab.vulkan

import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkEvent
import io.technoirlab.volk.vkDestroyEvent
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkEvent].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkEvent.html">VkEvent Manual Page</a>
 */
class Event(
    private val device: VkDevice,
    override val handle: VkEvent
) : Object<VkEvent> {

    /**
     * Destroy the event.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyEvent.html">vkDestroyEvent Manual Page</a>
     */
    override fun close() {
        vkDestroyEvent!!(device, handle, null)
    }
}
