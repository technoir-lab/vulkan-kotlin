package io.technoirlab.vulkan.image

import io.technoirlab.volk.VkImageAspectFlags
import io.technoirlab.volk.VkImageSubresourceRange

/**
 * A range of image aspects, mip levels, and array layers.
 *
 * @property aspectMask The image aspects to access.
 * @property baseMipLevel The first mip level to access.
 * @property levelCount The number of mip levels, or VK_REMAINING_MIP_LEVELS.
 * @property baseArrayLayer The first array layer to access.
 * @property layerCount The number of array layers, or VK_REMAINING_ARRAY_LAYERS.
 */
data class ImageSubresourceRange(
    val aspectMask: VkImageAspectFlags,
    val baseMipLevel: UInt = 0u,
    val levelCount: UInt = 1u,
    val baseArrayLayer: UInt = 0u,
    val layerCount: UInt = 1u,
)

internal inline fun VkImageSubresourceRange.from(range: ImageSubresourceRange) {
    aspectMask = range.aspectMask
    baseMipLevel = range.baseMipLevel
    levelCount = range.levelCount
    baseArrayLayer = range.baseArrayLayer
    layerCount = range.layerCount
}
