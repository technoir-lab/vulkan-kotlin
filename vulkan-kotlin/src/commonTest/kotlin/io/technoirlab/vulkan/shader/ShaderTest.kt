package io.technoirlab.vulkan.shader

import io.technoirlab.volk.VK_ERROR_OUT_OF_HOST_MEMORY
import io.technoirlab.volk.VK_INCOMPATIBLE_SHADER_BINARY_EXT
import io.technoirlab.volk.VK_SHADER_CODE_TYPE_SPIRV_EXT
import io.technoirlab.volk.VK_SUCCESS
import io.technoirlab.volk.VkShaderCreateInfoEXT
import io.technoirlab.volk.VkShaderEXT
import io.technoirlab.vulkan.VulkanException
import io.technoirlab.vulkan.device.fakeDevice
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.rawValue
import kotlinx.cinterop.reinterpret
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class ShaderTest {
    @Test
    fun `successful creation wraps the created handles in order`(): Unit = memScoped {
        val device = fakeDevice()
        val handles = List<VkShaderEXT>(2) { alloc<ByteVar>().ptr.reinterpret() }

        val shaders = VK_SUCCESS.toShadersOrNull(device, handles, "Failed to create shaders")

        assertEquals(handles, shaders?.map { it.handle })
    }

    @Test
    fun `incompatible binary yields no shaders instead of failing`(): Unit = memScoped {
        val device = fakeDevice()

        val shaders = VK_INCOMPATIBLE_SHADER_BINARY_EXT.toShadersOrNull(device, listOf(null, null), "Failed to create shaders")

        assertNull(shaders)
    }

    @Test
    fun `failed creation throws`(): Unit = memScoped {
        val device = fakeDevice()

        val exception = assertFailsWith<VulkanException> {
            VK_ERROR_OUT_OF_HOST_MEMORY.toShadersOrNull(device, listOf(null), "Failed to create shaders")
        }

        assertEquals("Failed to create shaders: VK_ERROR_OUT_OF_HOST_MEMORY", exception.message)
    }

    @Test
    fun `successful SPIR-V creation wraps the created handles in order`(): Unit = memScoped {
        val device = fakeDevice()
        val handles = List<VkShaderEXT>(2) { alloc<ByteVar>().ptr.reinterpret() }

        val shaders = VK_SUCCESS.toShaders(device, handles, "Failed to create shaders")

        assertEquals(handles, shaders.map { it.handle })
    }

    @Test
    fun `incompatible binary result fails SPIR-V creation`(): Unit = memScoped {
        val device = fakeDevice()

        val exception = assertFailsWith<VulkanException> {
            VK_INCOMPATIBLE_SHADER_BINARY_EXT.toShaders(device, listOf(null, null), "Failed to create shaders")
        }

        assertEquals("Failed to create shaders: VK_INCOMPATIBLE_SHADER_BINARY_EXT", exception.message)
    }

    @Test
    fun `SPIR-V code is written to the create info`(): Unit = memScoped {
        val createInfo = alloc<VkShaderCreateInfoEXT>()
        val words = allocArray<UIntVar>(2)

        createInfo.setSpirvCode(words, size = 8uL, index = 0)

        assertEquals(VK_SHADER_CODE_TYPE_SPIRV_EXT, createInfo.codeType)
        assertEquals(8uL, createInfo.codeSize)
        assertEquals(words.rawValue, createInfo.pCode.rawValue)
    }

    @Test
    fun `create info that already has code is rejected`(): Unit = memScoped {
        val words = allocArray<UIntVar>(2)
        val createInfo = alloc<VkShaderCreateInfoEXT> { pCode = words }

        val error = assertFailsWith<AssertionError> { createInfo.setSpirvCode(words, size = 8uL, index = 1) }

        assertEquals("createInfo must leave pCode and codeSize of the shader at index 1 unset", error.message)
    }
}
