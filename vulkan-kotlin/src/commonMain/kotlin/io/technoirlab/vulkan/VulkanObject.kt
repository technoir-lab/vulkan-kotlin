package io.technoirlab.vulkan

import io.technoirlab.volk.VkObjectType

/**
 * Represents a Vulkan object.
 */
interface VulkanObject : AutoCloseable {
    /**
     * The native handle to the Vulkan object.
     */
    val handle: VulkanHandle

    /**
     * The type of the Vulkan object.
     */
    val type: VkObjectType
}
