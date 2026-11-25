package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_ERROR_OUT_OF_HOST_MEMORY
import io.technoirlab.volk.VK_PIPELINE_COMPILE_REQUIRED
import io.technoirlab.volk.VK_SUCCESS
import io.technoirlab.volk.VkPipeline
import io.technoirlab.vulkan.VulkanException
import io.technoirlab.vulkan.device.fakeDevice
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class PipelineTest {
    @Test
    fun `successful creation wraps the created handle`(): Unit = memScoped {
        val device = fakeDevice()
        val handle: VkPipeline = alloc<ByteVar>().ptr.reinterpret()

        val pipeline = VK_SUCCESS.toPipelineOrNull(device, handle, "Failed to create pipeline")

        assertEquals(handle, pipeline?.handle)
    }

    @Test
    fun `required compile yields no pipeline instead of failing`(): Unit = memScoped {
        val device = fakeDevice()

        val pipeline = VK_PIPELINE_COMPILE_REQUIRED.toPipelineOrNull(device, pipeline = null, "Failed to create pipeline")

        assertNull(pipeline)
    }

    @Test
    fun `failed creation throws`(): Unit = memScoped {
        val device = fakeDevice()

        val exception = assertFailsWith<VulkanException> {
            VK_ERROR_OUT_OF_HOST_MEMORY.toPipelineOrNull(device, pipeline = null, "Failed to create pipeline")
        }

        assertEquals("Failed to create pipeline: VK_ERROR_OUT_OF_HOST_MEMORY", exception.message)
    }
}
