package io.technoirlab.vulkan

import io.technoirlab.volk.VkOffset3D

/**
 * An offset in three-dimensional coordinates.
 *
 * @property x The horizontal offset.
 * @property y The vertical offset.
 * @property z The depth offset.
 */
data class Offset3D(
    val x: Int,
    val y: Int,
    val z: Int,
)

internal inline fun VkOffset3D.from(offset: Offset3D) {
    x = offset.x
    y = offset.y
    z = offset.z
}
