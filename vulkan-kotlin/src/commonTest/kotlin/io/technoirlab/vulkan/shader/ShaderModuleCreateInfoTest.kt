package io.technoirlab.vulkan.shader

import io.technoirlab.volk.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.rawValue
import kotlinx.cinterop.toLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ShaderModuleCreateInfoTest {
    @Test
    fun `aligned code is described without a copy`(): Unit = memScoped {
        val code = allocSpirvCode(MODULE)

        val createInfo = shaderModuleCreateInfo(code)

        assertEquals(VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO, createInfo.sType)
        assertNull(createInfo.pNext)
        assertEquals(code.size, createInfo.codeSize)
        assertEquals(code.address.rawValue, createInfo.pCode.rawValue)
    }

    @Test
    fun `misaligned code is described through an aligned copy`(): Unit = memScoped {
        val code = allocSpirvCode(MODULE, misalignment = 1)

        val createInfo = shaderModuleCreateInfo(code)

        val words = assertNotNull(createInfo.pCode)
        assertNotEquals(code.address.rawValue, words.rawValue)
        assertEquals(0L, words.toLong() % UInt.SIZE_BYTES)
        assertEquals(code.size, createInfo.codeSize)
        assertEquals(MODULE, List(MODULE.size) { words[it] })
    }

    private companion object {
        // The header of a SPIR-V module without instructions: magic number, version 1.0, generator, bound, schema.
        private val MODULE = listOf(0x07230203u, 0x00010000u, 0x00000000u, 0x00000001u, 0x00000000u)
    }
}
