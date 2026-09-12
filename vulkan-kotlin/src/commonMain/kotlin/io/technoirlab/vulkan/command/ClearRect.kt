package io.technoirlab.vulkan.command

import io.technoirlab.volk.VkClearRect
import io.technoirlab.vulkan.Rect2D
import io.technoirlab.vulkan.from

/**
 * A rectangular region and array layers to clear in an attachment.
 *
 * @property rect The offset and dimensions of the region.
 * @property baseArrayLayer The first array layer to clear.
 * @property layerCount The number of array layers to clear.
 */
data class ClearRect(
    val rect: Rect2D,
    val baseArrayLayer: UInt = 0u,
    val layerCount: UInt = 1u,
)

internal inline fun VkClearRect.from(region: ClearRect) {
    rect.from(region.rect)
    baseArrayLayer = region.baseArrayLayer
    layerCount = region.layerCount
}
