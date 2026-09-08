package io.technoirlab.vulkan

import io.technoirlab.volk.VkViewport

/**
 * The viewport transformation parameters.
 *
 * @property x The horizontal viewport origin.
 * @property y The vertical viewport origin.
 * @property width The viewport width.
 * @property height The viewport height, which may be negative to invert the vertical axis.
 * @property minDepth The minimum viewport depth.
 * @property maxDepth The maximum viewport depth.
 */
data class Viewport(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val minDepth: Float = 0f,
    val maxDepth: Float = 1f,
)

internal inline fun VkViewport.from(viewport: Viewport) {
    x = viewport.x
    y = viewport.y
    width = viewport.width
    height = viewport.height
    minDepth = viewport.minDepth
    maxDepth = viewport.maxDepth
}
