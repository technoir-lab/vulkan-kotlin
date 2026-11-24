package io.technoirlab.vulkan.shader

import io.technoirlab.vulkan.memory.MemoryRegion
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toLong
import platform.posix.memcpy
import kotlin.assert

private const val SPIRV_MAGIC_NUMBER = 0x07230203u
private const val SPIRV_MAGIC_NUMBER_SWAPPED = 0x03022307u
private const val SPIRV_WORD_SIZE = UInt.SIZE_BYTES

/**
 * Returns [code] as SPIR-V words aligned the way Vulkan requires.
 *
 * Aligned code is returned as it is, without a copy, so the returned words are valid for as long as [code] is.
 * Any other code is copied into storage allocated from this placement, and the returned words are valid for as
 * long as the placement is.
 *
 * [shaderModuleCreateInfo] documents the requirements on [code], which are asserted here.
 *
 * @param code The memory region holding the SPIR-V code.
 * @param index The position of [code] in a batch, which the assertion messages then name, or `null` for a single
 * region.
 */
internal fun NativePlacement.alignedSpirvWords(code: MemoryRegion, index: Int? = null): CPointer<UIntVar> {
    assert(code.size > 0uL) { "${spirvCodeName(index)} must not be empty" }
    assert(code.size % SPIRV_WORD_SIZE.toUInt() == 0uL) { "${spirvCodeName(index)} must have a size that is a multiple of 4 bytes" }

    val words = if (code.isWordAligned) code.address.reinterpret<UIntVar>() else copySpirvWords(code)
    assert(words[0] == SPIRV_MAGIC_NUMBER) {
        if (words[0] == SPIRV_MAGIC_NUMBER_SWAPPED) {
            "${spirvCodeName(index)} must be in host byte order"
        } else {
            "${spirvCodeName(index)} must start with the SPIR-V magic number"
        }
    }
    return words
}

/**
 * Returns every region in [code] as SPIR-V words aligned the way Vulkan requires, in [code] order.
 *
 * Equal regions share their words, so a region that occurs several times is checked once and, if it is not
 * aligned, copied once. Each region is otherwise handled as [alignedSpirvWords] handles a single one, and an
 * assertion message names the index of the region it is about.
 */
internal fun NativePlacement.alignedSpirvWords(code: List<MemoryRegion>): List<CPointer<UIntVar>> {
    val wordsByRegion = HashMap<MemoryRegion, CPointer<UIntVar>>()
    return code.mapIndexed { index, region -> wordsByRegion.getOrPut(region) { alignedSpirvWords(region, index) } }
}

private val MemoryRegion.isWordAligned: Boolean
    get() = address.toLong() % SPIRV_WORD_SIZE == 0L

private fun NativePlacement.copySpirvWords(code: MemoryRegion): CPointer<UIntVar> {
    // Rounding up keeps the copy inside its allocation even for a size that is not a multiple of 4,
    // which reaches this point in a binary built without assertions.
    val wordCount = (code.size.toLong() + SPIRV_WORD_SIZE - 1) / SPIRV_WORD_SIZE
    val words = allocArray<UIntVar>(wordCount)
    memcpy(words, code.address, code.size.convert())
    return words
}

private fun spirvCodeName(index: Int?): String = if (index == null) "SPIR-V code" else "SPIR-V code at index $index"
