package io.technoirlab.vulkan.memory

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer

/**
 * A contiguous region of host memory.
 *
 * This value does not allocate, own, or free memory. The caller must keep the region valid for every operation
 * that uses it and provide the access permissions and alignment required by that operation. The memory may
 * belong to a native allocation, a pinned array, or a mapped file.
 *
 * @property address The address of the first byte in the region.
 * @property size The size of the region in bytes.
 */
data class MemoryRegion(
    val address: CPointer<out CPointed>,
    val size: ULong,
)
