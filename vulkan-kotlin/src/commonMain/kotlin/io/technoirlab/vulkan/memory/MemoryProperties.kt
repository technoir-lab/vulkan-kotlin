package io.technoirlab.vulkan.memory

import io.technoirlab.volk.VkMemoryHeap
import io.technoirlab.volk.VkMemoryHeapFlags
import io.technoirlab.volk.VkMemoryPropertyFlags
import io.technoirlab.volk.VkMemoryType
import io.technoirlab.volk.VkPhysicalDeviceMemoryProperties
import kotlinx.cinterop.get

/**
 * Memory types and heaps available on a physical device.
 *
 * @property memoryTypes The supported memory types, in native memory type index order.
 * @property memoryHeaps The available memory heaps, in native memory heap index order.
 */
data class MemoryProperties internal constructor(
    val memoryTypes: List<MemoryType>,
    val memoryHeaps: List<MemoryHeap>,
)

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

/**
 * A memory heap available on a physical device.
 *
 * @property size The total size of the heap in bytes.
 * @property flags The properties of the heap.
 */
data class MemoryHeap internal constructor(
    val size: ULong,
    val flags: VkMemoryHeapFlags,
)

internal inline fun VkPhysicalDeviceMemoryProperties.toMemoryProperties(): MemoryProperties = MemoryProperties(
    memoryTypes = List(memoryTypeCount.toInt()) { index ->
        memoryTypes[index].toMemoryType()
    },
    memoryHeaps = List(memoryHeapCount.toInt()) { index ->
        memoryHeaps[index].toMemoryHeap()
    },
)

internal inline fun VkMemoryType.toMemoryType(): MemoryType = MemoryType(
    propertyFlags = propertyFlags,
    heapIndex = heapIndex,
)

internal inline fun VkMemoryHeap.toMemoryHeap(): MemoryHeap = MemoryHeap(
    size = size,
    flags = flags,
)
