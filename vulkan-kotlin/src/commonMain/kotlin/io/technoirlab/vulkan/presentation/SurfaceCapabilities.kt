package io.technoirlab.vulkan.presentation

import io.technoirlab.volk.VkCompositeAlphaFlagsKHR
import io.technoirlab.volk.VkImageUsageFlags
import io.technoirlab.volk.VkSurfaceCapabilitiesKHR
import io.technoirlab.volk.VkSurfaceTransformFlagBitsKHR
import io.technoirlab.volk.VkSurfaceTransformFlagsKHR
import io.technoirlab.vulkan.Extent2D
import io.technoirlab.vulkan.toExtent2D

/**
 * Capabilities of a presentation surface on a physical device.
 *
 * @property minImageCount The minimum number of swapchain images.
 * @property maxImageCount The maximum number of swapchain images, or null if no limit is specified.
 * @property currentExtent The current surface dimensions, or null if the application chooses the swapchain extent.
 * @property minImageExtent The minimum swapchain image dimensions.
 * @property maxImageExtent The maximum swapchain image dimensions.
 * @property maxImageArrayLayers The maximum number of layers in each swapchain image.
 * @property supportedTransforms The supported presentation transforms.
 * @property currentTransform The current surface transform.
 * @property supportedCompositeAlpha The supported alpha compositing modes.
 * @property supportedUsageFlags The supported swapchain image usages for non-shared presentation modes.
 */
data class SurfaceCapabilities internal constructor(
    val minImageCount: UInt,
    val maxImageCount: UInt?,
    val currentExtent: Extent2D?,
    val minImageExtent: Extent2D,
    val maxImageExtent: Extent2D,
    val maxImageArrayLayers: UInt,
    val supportedTransforms: VkSurfaceTransformFlagsKHR,
    val currentTransform: VkSurfaceTransformFlagBitsKHR,
    val supportedCompositeAlpha: VkCompositeAlphaFlagsKHR,
    val supportedUsageFlags: VkImageUsageFlags,
)

internal inline fun VkSurfaceCapabilitiesKHR.toSurfaceCapabilities(): SurfaceCapabilities = SurfaceCapabilities(
    minImageCount = minImageCount,
    maxImageCount = maxImageCount.takeUnless { it == 0u },
    currentExtent = if (currentExtent.width == UInt.MAX_VALUE && currentExtent.height == UInt.MAX_VALUE) {
        null
    } else {
        currentExtent.toExtent2D()
    },
    minImageExtent = minImageExtent.toExtent2D(),
    maxImageExtent = maxImageExtent.toExtent2D(),
    maxImageArrayLayers = maxImageArrayLayers,
    supportedTransforms = supportedTransforms,
    currentTransform = currentTransform,
    supportedCompositeAlpha = supportedCompositeAlpha,
    supportedUsageFlags = supportedUsageFlags,
)
