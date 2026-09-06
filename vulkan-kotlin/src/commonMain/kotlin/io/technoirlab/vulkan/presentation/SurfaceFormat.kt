package io.technoirlab.vulkan.presentation

import io.technoirlab.volk.VkColorSpaceKHR
import io.technoirlab.volk.VkFormat
import io.technoirlab.volk.VkSurfaceFormatKHR

/**
 * An image format and color space supported by a presentation surface.
 *
 * @property format The supported image format.
 * @property colorSpace The color space compatible with the format.
 */
data class SurfaceFormat internal constructor(
    val format: VkFormat,
    val colorSpace: VkColorSpaceKHR,
)

internal fun VkSurfaceFormatKHR.toSurfaceFormat(): SurfaceFormat = SurfaceFormat(
    format = format,
    colorSpace = colorSpace,
)
