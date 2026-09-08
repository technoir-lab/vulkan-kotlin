package io.technoirlab.vulkan.pipeline

/**
 * A custom sample location within a pixel.
 *
 * @property x The horizontal sample coordinate.
 * @property y The vertical sample coordinate.
 */
data class SampleLocation(
    val x: Float,
    val y: Float,
)
