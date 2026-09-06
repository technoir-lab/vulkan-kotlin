package io.technoirlab.vulkan.image

import io.technoirlab.volk.VkImageFormatProperties
import io.technoirlab.volk.VkSampleCountFlags
import io.technoirlab.vulkan.Extent3D
import io.technoirlab.vulkan.toExtent3D

/**
 * Limits supported by an image format for a particular image configuration.
 *
 * @property maxExtent The maximum image dimensions.
 * @property maxMipLevels The maximum number of mip levels.
 * @property maxArrayLayers The maximum number of array layers.
 * @property sampleCounts The supported sample counts.
 * @property maxResourceSize The maximum image resource size in bytes.
 */
data class ImageFormatProperties internal constructor(
    val maxExtent: Extent3D,
    val maxMipLevels: UInt,
    val maxArrayLayers: UInt,
    val sampleCounts: VkSampleCountFlags,
    val maxResourceSize: ULong,
)

internal inline fun VkImageFormatProperties.toImageFormatProperties(): ImageFormatProperties = ImageFormatProperties(
    maxExtent = maxExtent.toExtent3D(),
    maxMipLevels = maxMipLevels,
    maxArrayLayers = maxArrayLayers,
    sampleCounts = sampleCounts,
    maxResourceSize = maxResourceSize,
)
