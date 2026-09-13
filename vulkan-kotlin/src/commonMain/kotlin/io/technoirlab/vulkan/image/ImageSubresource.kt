package io.technoirlab.vulkan.image

import io.technoirlab.volk.VkImageAspectFlags
import io.technoirlab.volk.VkImageSubresourceLayers
import io.technoirlab.volk.VkImageSubresourceRange
import io.technoirlab.volk.VkSubresourceLayout

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

/**
 * Memory layout of an image subresource.
 *
 * @property offset The byte offset of the subresource from the start of the image.
 * @property size The size of the subresource in bytes, including any padding.
 * @property rowPitch The byte distance between consecutive rows of texels or compressed texel blocks.
 * @property arrayPitch The byte distance between consecutive array layers.
 * @property depthPitch The byte distance between consecutive depth slices.
 * @property hostMemcpySize The number of bytes required for a host image copy using the memcpy flag.
 */
data class ImageSubresourceLayout internal constructor(
    val offset: ULong,
    val size: ULong,
    val rowPitch: ULong,
    val arrayPitch: ULong,
    val depthPitch: ULong,
    val hostMemcpySize: ULong,
)

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

internal inline fun VkImageSubresourceLayers.from(subresource: ImageSubresourceLayers) {
    aspectMask = subresource.aspectMask
    mipLevel = subresource.mipLevel
    baseArrayLayer = subresource.baseArrayLayer
    layerCount = subresource.layerCount
}

internal inline fun VkSubresourceLayout.toImageSubresourceLayout(hostMemcpySize: ULong): ImageSubresourceLayout = ImageSubresourceLayout(
    offset = offset,
    size = size,
    rowPitch = rowPitch,
    arrayPitch = arrayPitch,
    depthPitch = depthPitch,
    hostMemcpySize = hostMemcpySize,
)

internal inline fun VkImageSubresourceRange.from(range: ImageSubresourceRange) {
    aspectMask = range.aspectMask
    baseMipLevel = range.baseMipLevel
    levelCount = range.levelCount
    baseArrayLayer = range.baseArrayLayer
    layerCount = range.layerCount
}
