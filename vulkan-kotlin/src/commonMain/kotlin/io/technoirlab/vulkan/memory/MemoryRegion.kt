package io.technoirlab.vulkan.memory

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlin.assert

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

/**
 * Runs [block] with a region that covers this array from [startIndex] until [endIndex].
 *
 * The array is pinned only while [block] runs, so the region must not be used after [block] returns. The address
 * of the region has no particular alignment.
 *
 * The range must lie within the array and must not be empty, because an empty range has no address.
 *
 * @param startIndex The index of the first byte in the region.
 * @param endIndex The index after the last byte in the region.
 * @param block Uses the region.
 * @return The result of [block].
 */
inline fun <R> ByteArray.useMemoryRegion(startIndex: Int = 0, endIndex: Int = size, block: (MemoryRegion) -> R): R {
    assert(startIndex in 0..<endIndex && endIndex <= size) { "the range must not be empty and must lie within the array" }

    return usePinned { pinned ->
        block(MemoryRegion(pinned.addressOf(startIndex), (endIndex - startIndex).toULong()))
    }
}
