package io.technoirlab.vulkan

import io.technoirlab.volk.VK_API_VERSION_1_4
import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VK_VERSION_MAJOR
import io.technoirlab.volk.VK_VERSION_MINOR
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue

class VulkanTest {
    private val vulkan = Vulkan()

    @AfterTest
    fun tearDown() {
        vulkan.close()
    }

    @Test
    fun `create instance`() = memScoped {
        val hasPortabilityExtension = vulkan.enumerateInstanceExtensionProperties().any {
            it.extensionName.toKString() == VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
        }
        val extensions = if (hasPortabilityExtension) {
            listOf(VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)
        } else {
            emptyList()
        }
        val applicationInfo = ApplicationInfo(apiVersion = minOf(vulkan.instanceVersion, VK_API_VERSION_1_4))
        val instance = vulkan.createInstance(applicationInfo, enabledExtensions = extensions)
        instance.close()
    }

    @Test
    fun `get instance version`() = memScoped {
        val instanceVersion = vulkan.instanceVersion

        assertTrue(VK_VERSION_MAJOR(instanceVersion) >= 1u)
        assertTrue(VK_VERSION_MINOR(instanceVersion) >= 3u)
    }

    @Test
    fun `enumerate instance extensions`() = memScoped {
        val extensions = vulkan.enumerateInstanceExtensionProperties().toList()

        assertTrue(extensions.isNotEmpty())
    }

    @Test
    fun `enumerate instance layers`() = memScoped {
        val extensions = vulkan.enumerateInstanceLayerProperties().toList()

        assertTrue(extensions.isNotEmpty())
    }
}
