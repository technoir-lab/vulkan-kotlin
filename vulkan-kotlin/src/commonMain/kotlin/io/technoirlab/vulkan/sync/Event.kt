package io.technoirlab.vulkan.sync

import io.technoirlab.volk.VK_OBJECT_TYPE_EVENT
import io.technoirlab.volk.VkEvent
import io.technoirlab.volk.VkObjectType
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.device.Device
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkEvent].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkEvent.html">VkEvent Manual Page</a>
 */
class Event internal constructor(
    private val device: Device,
    override val handle: VkEvent,
) : VulkanObject,
    AutoCloseable {
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
        device.dispatch.vkDestroyEvent!!(device.handle, handle, null)
    }
}
