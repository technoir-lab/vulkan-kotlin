package io.technoirlab.vulkan

import io.technoirlab.volk.VkQueueFamilyProperties
import io.technoirlab.volk.VkQueueFlags

/**
 * Capabilities of a queue family on a physical device.
 *
 * @property queueFlags The operations supported by queues in this family.
 * @property queueCount The number of queues available in this family.
 * @property timestampValidBits The number of valid timestamp bits, or zero if timestamps are unsupported.
 * @property minImageTransferGranularity The minimum image transfer granularity in texels or compressed texel blocks.
 */
data class QueueFamilyProperties internal constructor(
    val queueFlags: VkQueueFlags,
    val queueCount: UInt,
    val timestampValidBits: UInt,
    val minImageTransferGranularity: Extent3D,
)

internal fun VkQueueFamilyProperties.toQueueFamilyProperties(): QueueFamilyProperties = QueueFamilyProperties(
    queueFlags = queueFlags,
    queueCount = queueCount,
    timestampValidBits = timestampValidBits,
    minImageTransferGranularity = minImageTransferGranularity.toExtent3D(),
)
