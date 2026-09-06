package io.technoirlab.vulkan.memory

import io.technoirlab.volk.VkMemoryRequirements

/**
 * Memory requirements for a buffer or image.
 *
 * @property size The required memory size in bytes.
 * @property alignment The required alignment of the binding offset in bytes.
 * @property memoryTypeBits A bitmask of compatible memory types, with each bit corresponding to a memory type index.
 */
data class MemoryRequirements internal constructor(
    val size: ULong,
    val alignment: ULong,
    val memoryTypeBits: UInt,
)

internal fun VkMemoryRequirements.toMemoryRequirements(): MemoryRequirements = MemoryRequirements(
    size = size,
    alignment = alignment,
    memoryTypeBits = memoryTypeBits,
)
