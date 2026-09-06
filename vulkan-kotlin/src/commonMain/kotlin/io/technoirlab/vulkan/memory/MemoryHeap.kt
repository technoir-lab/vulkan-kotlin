package io.technoirlab.vulkan.memory

import io.technoirlab.volk.VkMemoryHeap
import io.technoirlab.volk.VkMemoryHeapFlags

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

internal inline fun VkMemoryHeap.toMemoryHeap(): MemoryHeap = MemoryHeap(
    size = size,
    flags = flags,
)
