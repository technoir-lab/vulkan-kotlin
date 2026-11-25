package io.technoirlab.vulkan

import io.technoirlab.volk.VK_KHR_SURFACE_EXTENSION_NAME
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class InstanceTest {
    private val vulkan = Vulkan()

    @AfterTest
    fun tearDown() {
        vulkan.close()
    }

    @Test
    fun `instance keeps the commands of its extensions after another instance is created`() {
        vulkan.createTestInstance(listOf(VK_KHR_SURFACE_EXTENSION_NAME)).use { surfaceInstance ->
            vulkan.createTestInstance().use { plainInstance ->
                assertNotNull(surfaceInstance.dispatch.vkDestroySurfaceKHR)
                assertNull(plainInstance.dispatch.vkDestroySurfaceKHR)
            }
        }
    }

    @Test
    fun `closed instance rejects further calls`() {
        val instance = vulkan.createTestInstance()
        instance.close()

        assertFailsWith<NullPointerException> {
            instance.enumeratePhysicalDevices()
        }
    }
}
