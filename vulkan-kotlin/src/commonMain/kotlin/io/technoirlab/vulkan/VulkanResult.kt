package io.technoirlab.vulkan

import io.technoirlab.volk.VK_SUCCESS
import io.technoirlab.volk.VkResult
import io.technoirlab.volk.string_VkResult
import kotlinx.cinterop.toKString

data class VulkanResult<T> internal constructor(
    val payload: T,
    val status: VkResult
)

@Suppress("NOTHING_TO_INLINE")
inline fun VkResult.checkResult(message: String): Boolean {
    if (this != VK_SUCCESS) {
        throw VulkanException("$message: ${string_VkResult(this)?.toKString()}")
    }
    return true
}
