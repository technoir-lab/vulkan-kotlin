package io.technoirlab.vulkan

import io.technoirlab.volk.VkObjectType
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer

/**
 * Represents a Vulkan object.
 */
interface VulkanObject {
    /**
     * The native handle to the Vulkan object.
     */
    val handle: CPointer<out CPointed>

    /**
     * The type of the Vulkan object.
     */
    val type: VkObjectType
}
