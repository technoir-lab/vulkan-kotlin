package io.technoirlab.vulkan.image

import io.technoirlab.volk.VkSubresourceLayout

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

internal fun VkSubresourceLayout.toImageSubresourceLayout(hostMemcpySize: ULong): ImageSubresourceLayout = ImageSubresourceLayout(
    offset = offset,
    size = size,
    rowPitch = rowPitch,
    arrayPitch = arrayPitch,
    depthPitch = depthPitch,
    hostMemcpySize = hostMemcpySize,
)
