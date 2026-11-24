package io.technoirlab.vulkan.shader

import io.technoirlab.vulkan.memory.MemoryRegion
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.plus
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlin.assert

/**
 * Allocates [words] as SPIR-V code that starts [misalignment] bytes past an address aligned to 4 bytes.
 *
 * The words are stored in little-endian order, which is the host byte order of every supported target.
 * [misalignment] must be less than 4, the size of the spare word the allocation reserves for it.
 */
internal fun NativePlacement.allocSpirvCode(words: List<UInt>, misalignment: Int = 0): MemoryRegion {
    assert(misalignment in 0..<UInt.SIZE_BYTES) { "misalignment must be between 0 and 3" }

    val bytes = allocArray<UIntVar>(words.size + 1).reinterpret<ByteVar>()
    words.forEachIndexed { wordIndex, word ->
        repeat(UInt.SIZE_BYTES) { byteIndex ->
            val offset = misalignment + wordIndex * UInt.SIZE_BYTES + byteIndex
            bytes[offset] = (word shr (byteIndex * Byte.SIZE_BITS)).toByte()
        }
    }
    return MemoryRegion((bytes + misalignment)!!, (words.size * UInt.SIZE_BYTES).toULong())
}

/**
 * Returns [words] as the bytes of a `.spv` file, which stores each word in little-endian order.
 */
internal fun spirvBytes(words: List<UInt>): ByteArray = ByteArray(words.size * UInt.SIZE_BYTES) { index ->
    (words[index / UInt.SIZE_BYTES] shr (index % UInt.SIZE_BYTES * Byte.SIZE_BITS)).toByte()
}
