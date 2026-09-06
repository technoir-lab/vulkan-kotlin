package io.technoirlab.vulkan

import io.technoirlab.volk.VkFormatFeatureFlags

/**
 * Features supported by a Vulkan format.
 *
 * @property linearTilingFeatures The features supported by images with linear tiling.
 * @property optimalTilingFeatures The features supported by images with optimal tiling.
 * @property bufferFeatures The features supported by buffers.
 */
data class FormatProperties internal constructor(
    val linearTilingFeatures: VkFormatFeatureFlags,
    val optimalTilingFeatures: VkFormatFeatureFlags,
    val bufferFeatures: VkFormatFeatureFlags,
)
