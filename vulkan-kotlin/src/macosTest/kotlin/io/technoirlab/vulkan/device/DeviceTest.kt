package io.technoirlab.vulkan.device

import io.technoirlab.volk.VK_EXT_DEBUG_UTILS_EXTENSION_NAME
import io.technoirlab.volk.VK_KHR_SWAPCHAIN_EXTENSION_NAME
import io.technoirlab.vulkan.Vulkan
import io.technoirlab.vulkan.createTestDevice
import io.technoirlab.vulkan.createTestInstance
import io.technoirlab.vulkan.debug.setObjectName
import io.technoirlab.vulkan.findTestPhysicalDevice
import io.technoirlab.vulkan.withTestDevice
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DeviceTest {
    private val vulkan = Vulkan()

    @AfterTest
    fun tearDown() {
        vulkan.close()
    }

    @Test
    fun `device keeps the commands of its extensions after another device is created`() {
        vulkan.withTestDevice(extensions = listOf(VK_KHR_SWAPCHAIN_EXTENSION_NAME)) { physicalDevice, swapchainDevice ->
            createTestDevice(physicalDevice)!!.use { plainDevice ->
                assertNotNull(swapchainDevice.dispatch.vkCreateSwapchainKHR)
                assertNull(plainDevice.dispatch.vkCreateSwapchainKHR)
            }
        }
    }

    @Test
    fun `device stays usable after another device is closed`() {
        vulkan.withTestDevice { physicalDevice, device ->
            createTestDevice(physicalDevice)!!.close()

            device.createFence().use { fence ->
                assertFalse(fence.isSignaled)
            }
        }
    }

    @Test
    fun `closed device rejects further calls`() {
        vulkan.withTestDevice { physicalDevice, _ ->
            val device = createTestDevice(physicalDevice)!!
            device.close()

            assertFailsWith<NullPointerException> {
                device.waitIdle()
            }
        }
    }

    @Test
    fun `closed device rejects debug utils calls`() {
        vulkan.withTestDevice(instanceExtensions = listOf(VK_EXT_DEBUG_UTILS_EXTENSION_NAME)) { physicalDevice, _ ->
            val device = createTestDevice(physicalDevice)!!
            device.close()

            assertFailsWith<NullPointerException> {
                device.setObjectName(device, "closed")
            }
        }
    }

    @Test
    fun `device of a closed Vulkan rejects further calls`() {
        val instance = vulkan.createTestInstance()
        val device = instance.findTestPhysicalDevice()?.let { createTestDevice(it) } ?: return
        vulkan.close()

        assertFailsWith<NullPointerException> {
            device.waitIdle()
        }
    }
}
