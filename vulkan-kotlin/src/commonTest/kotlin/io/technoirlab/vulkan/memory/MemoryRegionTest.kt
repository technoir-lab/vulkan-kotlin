package io.technoirlab.vulkan.memory

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MemoryRegionTest {
    @Test
    fun `region covers the whole array by default`() {
        val bytes = byteArrayOf(1, 2, 3, 4, 5)

        val contents = bytes.useMemoryRegion { it.toByteList() }

        assertEquals(listOf<Byte>(1, 2, 3, 4, 5), contents)
    }

    @Test
    fun `region covers the requested range`() {
        val bytes = byteArrayOf(1, 2, 3, 4, 5)

        val contents = bytes.useMemoryRegion(startIndex = 1, endIndex = 4) { it.toByteList() }

        assertEquals(listOf<Byte>(2, 3, 4), contents)
    }

    @Test
    fun `writes through the region reach the array`() {
        val bytes = byteArrayOf(1, 2, 3)

        bytes.useMemoryRegion(startIndex = 1) { it.address.reinterpret<ByteVar>()[0] = 9 }

        assertContentEquals(byteArrayOf(1, 9, 3), bytes)
    }

    @Test
    fun `empty range is rejected`() {
        val bytes = byteArrayOf(1, 2, 3)

        val error = assertFailsWith<AssertionError> { bytes.useMemoryRegion(startIndex = 2, endIndex = 2) {} }

        assertEquals("the range must not be empty and must lie within the array", error.message)
    }

    @Test
    fun `empty array is rejected`() {
        val bytes = byteArrayOf()

        val error = assertFailsWith<AssertionError> { bytes.useMemoryRegion {} }

        assertEquals("the range must not be empty and must lie within the array", error.message)
    }

    @Test
    fun `range beyond the array is rejected`() {
        val bytes = byteArrayOf(1, 2, 3)

        val error = assertFailsWith<AssertionError> { bytes.useMemoryRegion(endIndex = 4) {} }

        assertEquals("the range must not be empty and must lie within the array", error.message)
    }

    private fun MemoryRegion.toByteList(): List<Byte> {
        val bytes = address.reinterpret<ByteVar>()
        return List(size.toInt()) { bytes[it] }
    }
}
