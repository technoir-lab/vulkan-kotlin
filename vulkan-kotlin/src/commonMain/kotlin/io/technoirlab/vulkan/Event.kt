package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_EVENT
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkEvent
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkDestroyEvent
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkEvent].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkEvent.html">VkEvent Manual Page</a>
 */
class Event internal constructor(
    private val device: VkDevice,
    override val handle: VkEvent
) : Object<VkEvent> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_EVENT

    /**
     * Destroy the event.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyEvent.html">vkDestroyEvent Manual Page</a>
     */
    override fun close() {
        vkDestroyEvent!!(device, handle, null)
    }
}
