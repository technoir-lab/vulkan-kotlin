package io.technoirlab.vulkan.memory

/**
 * Memory budget and usage for a memory heap on a physical device.
 *
 * @property budget The estimated total memory, in bytes, available to the process on this heap.
 * @property usage The estimated memory, in bytes, currently used by the process on this heap.
 */
data class MemoryHeapBudget internal constructor(
    val budget: ULong,
    val usage: ULong,
)
