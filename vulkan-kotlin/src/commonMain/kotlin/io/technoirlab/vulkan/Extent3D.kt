package io.technoirlab.vulkan

import io.technoirlab.volk.VkExtent3D

/**
 * The dimensions of a three-dimensional region.
 *
 * @property width The width of the region.
 * @property height The height of the region.
 * @property depth The depth of the region.
 */
data class Extent3D(
    val width: UInt,
    val height: UInt,
    val depth: UInt,
)

internal inline fun VkExtent3D.toExtent3D(): Extent3D = Extent3D(
    width = width,
    height = height,
    depth = depth,
)
