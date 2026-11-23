package io.technoirlab.vulkan.memory

import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkMemoryDedicatedRequirements
import io.technoirlab.volk.VkMemoryRequirements

/**
 * Memory requirements for a buffer or image.
 *
 * @property size The required memory size in bytes.
 * @property alignment The required alignment of the binding offset in bytes.
 * @property memoryTypeBits A bitmask of compatible memory types, with each bit corresponding to a memory type index.
 * @property prefersDedicatedAllocation Whether the implementation prefers a dedicated memory allocation for this resource.
 * @property requiresDedicatedAllocation Whether the implementation requires a dedicated memory allocation for this resource.
 */
data class MemoryRequirements internal constructor(
    val size: ULong,
    val alignment: ULong,
    val memoryTypeBits: UInt,
    val prefersDedicatedAllocation: Boolean,
    val requiresDedicatedAllocation: Boolean,
)

internal inline fun VkMemoryRequirements.toMemoryRequirements(dedicatedRequirements: VkMemoryDedicatedRequirements): MemoryRequirements =
    MemoryRequirements(
        size = size,
        alignment = alignment,
        memoryTypeBits = memoryTypeBits,
        prefersDedicatedAllocation = dedicatedRequirements.prefersDedicatedAllocation == VK_TRUE,
        requiresDedicatedAllocation = dedicatedRequirements.requiresDedicatedAllocation == VK_TRUE,
    )
