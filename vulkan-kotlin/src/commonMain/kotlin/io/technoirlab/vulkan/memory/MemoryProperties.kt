package io.technoirlab.vulkan.memory

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

internal fun VkPhysicalDeviceMemoryProperties.toMemoryProperties(): MemoryProperties = MemoryProperties(
    memoryTypes = List(memoryTypeCount.toInt()) { index ->
        memoryTypes[index].toMemoryType()
    },
    memoryHeaps = List(memoryHeapCount.toInt()) { index ->
        memoryHeaps[index].toMemoryHeap()
    },
)
