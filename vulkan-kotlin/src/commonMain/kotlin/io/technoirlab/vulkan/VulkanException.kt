package io.technoirlab.vulkan

/**
 * Exception thrown when a Vulkan operation fails.
 */
class VulkanException @PublishedApi internal constructor(
    message: String,
) : Exception(message)
