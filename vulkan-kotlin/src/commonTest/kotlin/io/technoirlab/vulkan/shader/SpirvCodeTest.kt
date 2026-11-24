package io.technoirlab.vulkan.shader

import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.rawValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals

class SpirvCodeTest {
    @Test
    fun `aligned code is used without a copy`(): Unit = memScoped {
        val code = allocSpirvCode(MODULE)

        val words = alignedSpirvWords(code)

        assertEquals(code.address.rawValue, words.rawValue)
    }

    @Test
    fun `misaligned code is copied into aligned storage`(): Unit = memScoped {
        val codes = (1..3).map { allocSpirvCode(MODULE, misalignment = it) }

        val copies = codes.map { alignedSpirvWords(it) }

        copies.forEachIndexed { index, copy ->
            assertNotEquals(codes[index].address.rawValue, copy.rawValue)
            assertEquals(0L, copy.rawValue.toLong() % UInt.SIZE_BYTES)
            assertEquals(MODULE, List(MODULE.size) { copy[it] })
        }
    }

    @Test
    fun `empty code is rejected`(): Unit = memScoped {
        val code = allocSpirvCode(MODULE).copy(size = 0uL)

        val error = assertFailsWith<AssertionError> { alignedSpirvWords(code) }

        assertEquals("SPIR-V code must not be empty", error.message)
    }

    @Test
    fun `code that ends with an incomplete word is rejected`(): Unit = memScoped {
        val completeCode = allocSpirvCode(MODULE)
        val code = completeCode.copy(size = completeCode.size - 1uL)

        val error = assertFailsWith<AssertionError> { alignedSpirvWords(code) }

        assertEquals("SPIR-V code must have a size that is a multiple of 4 bytes", error.message)
    }

    @Test
    fun `code that does not start with the magic number is rejected`(): Unit = memScoped {
        val code = allocSpirvCode(listOf(0xcafebabeu) + MODULE.drop(1))

        val error = assertFailsWith<AssertionError> { alignedSpirvWords(code) }

        assertEquals("SPIR-V code must start with the SPIR-V magic number", error.message)
    }

    @Test
    fun `code in the other byte order is rejected`(): Unit = memScoped {
        val code = allocSpirvCode(listOf(0x03022307u) + MODULE.drop(1))

        val error = assertFailsWith<AssertionError> { alignedSpirvWords(code) }

        assertEquals("SPIR-V code must be in host byte order", error.message)
    }

    @Test
    fun `misaligned code is checked like aligned code`(): Unit = memScoped {
        val code = allocSpirvCode(listOf(0x03022307u) + MODULE.drop(1), misalignment = 1)

        val error = assertFailsWith<AssertionError> { alignedSpirvWords(code) }

        assertEquals("SPIR-V code must be in host byte order", error.message)
    }

    @Test
    fun `regions are returned as words in their order`(): Unit = memScoped {
        val misalignedCode = allocSpirvCode(MODULE, misalignment = 1)
        val alignedCode = allocSpirvCode(MODULE)

        val words = alignedSpirvWords(listOf(misalignedCode, alignedCode))

        assertEquals(2, words.size)
        assertNotEquals(misalignedCode.address.rawValue, words[0].rawValue)
        assertEquals(alignedCode.address.rawValue, words[1].rawValue)
    }

    @Test
    fun `misaligned region used several times is copied once`(): Unit = memScoped {
        val code = allocSpirvCode(MODULE, misalignment = 1)

        val words = alignedSpirvWords(listOf(code, code.copy()))

        assertNotEquals(code.address.rawValue, words[0].rawValue)
        assertEquals(words[0].rawValue, words[1].rawValue)
    }

    @Test
    fun `rejected region of a batch is named by its index`(): Unit = memScoped {
        val validCode = allocSpirvCode(MODULE)
        val swappedCode = allocSpirvCode(listOf(0x03022307u) + MODULE.drop(1))

        val error = assertFailsWith<AssertionError> { alignedSpirvWords(listOf(validCode, swappedCode)) }

        assertEquals("SPIR-V code at index 1 must be in host byte order", error.message)
    }

    private companion object {
        // The header of a SPIR-V module without instructions: magic number, version 1.0, generator, bound, schema.
        private val MODULE = listOf(0x07230203u, 0x00010000u, 0x00000000u, 0x00000001u, 0x00000000u)
    }
}
