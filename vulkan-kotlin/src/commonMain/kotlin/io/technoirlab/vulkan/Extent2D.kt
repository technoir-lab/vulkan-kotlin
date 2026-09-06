package io.technoirlab.vulkan

import io.technoirlab.volk.VkExtent2D

/**
 * The dimensions of a two-dimensional region.
 *
 * @property width The width of the region.
 * @property height The height of the region.
 */
data class Extent2D(
    val width: UInt,
    val height: UInt,
)

internal inline fun VkExtent2D.toExtent2D(): Extent2D = Extent2D(
    width = width,
    height = height,
)
