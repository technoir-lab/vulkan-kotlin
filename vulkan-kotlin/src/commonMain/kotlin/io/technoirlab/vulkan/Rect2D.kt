package io.technoirlab.vulkan

import io.technoirlab.volk.VkRect2D

/**
 * The offset and dimensions of a two-dimensional rectangular region.
 *
 * @property x The horizontal offset of the region.
 * @property y The vertical offset of the region.
 * @property extent The dimensions of the region.
 */
data class Rect2D(
    val x: Int,
    val y: Int,
    val extent: Extent2D,
)

internal inline fun VkRect2D.from(rect: Rect2D) {
    offset.x = rect.x
    offset.y = rect.y
    extent.width = rect.extent.width
    extent.height = rect.extent.height
}
