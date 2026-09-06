package io.technoirlab.vulkan.memory

import io.technoirlab.volk.VkMemoryPropertyFlags
import io.technoirlab.volk.VkMemoryType

/**
 * A memory type supported by a physical device.
 *
 * @property propertyFlags The properties of allocations made from this memory type.
 * @property heapIndex The index of the backing heap in [MemoryProperties.memoryHeaps].
 */
data class MemoryType internal constructor(
    val propertyFlags: VkMemoryPropertyFlags,
    val heapIndex: UInt,
)

internal inline fun VkMemoryType.toMemoryType(): MemoryType = MemoryType(
    propertyFlags = propertyFlags,
    heapIndex = heapIndex,
)
