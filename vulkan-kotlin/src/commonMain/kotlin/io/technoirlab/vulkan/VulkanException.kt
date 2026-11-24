package io.technoirlab.vulkan

/**
 * Exception thrown when a Vulkan operation fails.
 */
class VulkanException internal constructor(
    message: String,
) : Exception(message)
