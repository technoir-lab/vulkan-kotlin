package io.technoirlab.vulkan.image

import io.technoirlab.volk.VkImageAspectFlags
import io.technoirlab.volk.VkImageSubresourceLayers

/**
 * The image aspects and array layers at a single mip level.
 *
 * @property aspectMask The image aspects to access.
 * @property mipLevel The mip level to access.
 * @property baseArrayLayer The first array layer to access.
 * @property layerCount The number of array layers to access.
 */
data class ImageSubresourceLayers(
    val aspectMask: VkImageAspectFlags,
    val mipLevel: UInt = 0u,
    val baseArrayLayer: UInt = 0u,
    val layerCount: UInt = 1u,
)

internal inline fun VkImageSubresourceLayers.from(subresource: ImageSubresourceLayers) {
    aspectMask = subresource.aspectMask
    mipLevel = subresource.mipLevel
    baseArrayLayer = subresource.baseArrayLayer
    layerCount = subresource.layerCount
}
