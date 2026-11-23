package io.technoirlab.vulkan.shader

import io.technoirlab.volk.VK_ERROR_OUT_OF_HOST_MEMORY
import io.technoirlab.volk.VK_INCOMPATIBLE_SHADER_BINARY_EXT
import io.technoirlab.volk.VK_SUCCESS
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkShaderEXT
import io.technoirlab.vulkan.VulkanException
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class ShaderTest {
    @Test
    fun `successful creation wraps the created handles in order`(): Unit = memScoped {
        val device: VkDevice = alloc<ByteVar>().ptr.reinterpret()
        val handles = List<VkShaderEXT>(2) { alloc<ByteVar>().ptr.reinterpret() }

        val shaders = VK_SUCCESS.toShadersOrNull(device, handles, "Failed to create shaders")

        assertEquals(handles, shaders?.map { it.handle })
    }

    @Test
    fun `incompatible binary yields no shaders instead of failing`(): Unit = memScoped {
        val device: VkDevice = alloc<ByteVar>().ptr.reinterpret()

        val shaders = VK_INCOMPATIBLE_SHADER_BINARY_EXT.toShadersOrNull(device, listOf(null, null), "Failed to create shaders")

        assertNull(shaders)
    }

    @Test
    fun `failed creation throws`(): Unit = memScoped {
        val device: VkDevice = alloc<ByteVar>().ptr.reinterpret()

        val exception = assertFailsWith<VulkanException> {
            VK_ERROR_OUT_OF_HOST_MEMORY.toShadersOrNull(device, listOf(null), "Failed to create shaders")
        }

        assertEquals("Failed to create shaders: VK_ERROR_OUT_OF_HOST_MEMORY", exception.message)
    }
}
