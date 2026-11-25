package io.technoirlab.vulkan

import io.technoirlab.volk.VK_API_VERSION_1_4
import io.technoirlab.volk.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT
import io.technoirlab.volk.VK_EXT_LAYER_SETTINGS_EXTENSION_NAME
import io.technoirlab.volk.VK_EXT_MEMORY_BUDGET_EXTENSION_NAME
import io.technoirlab.volk.VK_FORMAT_R8G8B8A8_UNORM
import io.technoirlab.volk.VK_IMAGE_LAYOUT_UNDEFINED
import io.technoirlab.volk.VK_IMAGE_TILING_OPTIMAL
import io.technoirlab.volk.VK_IMAGE_TYPE_2D
import io.technoirlab.volk.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT
import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VK_LAYER_SETTING_TYPE_BOOL32_EXT
import io.technoirlab.volk.VK_MEMORY_PROPERTY_LAZILY_ALLOCATED_BIT
import io.technoirlab.volk.VK_SAMPLE_COUNT_1_BIT
import io.technoirlab.volk.VK_SHARING_MODE_EXCLUSIVE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_LAYER_SETTINGS_CREATE_INFO_EXT
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkBool32Var
import io.technoirlab.volk.VkLayerSettingEXT
import io.technoirlab.volk.VkLayerSettingsCreateInfoEXT
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VulkanTest {
    private val vulkan = Vulkan()

    @AfterTest
    fun tearDown() {
        vulkan.close()
    }

    @Test
    fun `create instance`() {
        vulkan.createTestInstance().use { }
    }

    @Test
    fun `second live Vulkan object is rejected`() {
        assertFailsWith<AssertionError> {
            Vulkan()
        }
    }

    @Test
    fun `instance of a closed Vulkan rejects further calls`() {
        val instance = vulkan.createTestInstance()
        vulkan.close()

        assertFailsWith<NullPointerException> {
            instance.enumeratePhysicalDevices()
        }
    }

    @Test
    fun `create instance with layer settings extension chain`() {
        val validationLayer = "VK_LAYER_KHRONOS_validation"
        val hasValidationLayer = vulkan.enumerateInstanceLayerProperties().any { it.name == validationLayer }
        if (!hasValidationLayer) return
        val hasLayerSettings = vulkan.enumerateInstanceExtensionProperties(validationLayer).any {
            it.name == VK_EXT_LAYER_SETTINGS_EXTENSION_NAME
        }
        if (!hasLayerSettings) return

        val hasPortabilityExtension = vulkan.enumerateInstanceExtensionProperties().any {
            it.name == VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
        }
        val extensions = buildList {
            if (hasPortabilityExtension) add(VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)
            add(VK_EXT_LAYER_SETTINGS_EXTENSION_NAME)
        }
        memScoped {
            val validateSync = alloc<VkBool32Var> { value = VK_TRUE }
            val layerSetting = alloc<VkLayerSettingEXT> {
                pLayerName = validationLayer.cstr.ptr
                pSettingName = "validate_sync".cstr.ptr
                type = VK_LAYER_SETTING_TYPE_BOOL32_EXT
                valueCount = 1u
                pValues = validateSync.ptr
            }
            val layerSettingsCreateInfo = alloc<VkLayerSettingsCreateInfoEXT> {
                sType = VK_STRUCTURE_TYPE_LAYER_SETTINGS_CREATE_INFO_EXT
                settingCount = 1u
                pSettings = layerSetting.ptr
            }
            vulkan.createInstance(
                applicationInfo = ApplicationInfo(),
                enabledLayers = listOf(validationLayer),
                enabledExtensions = extensions,
                extensions = layerSettingsCreateInfo.ptr,
            ).use { instance ->
                assertTrue(VK_EXT_LAYER_SETTINGS_EXTENSION_NAME in instance.enabledExtensions)
            }
        }
    }

    @Test
    fun `get instance version`() {
        val instanceVersion = vulkan.instanceVersion

        assertTrue(instanceVersion >= VK_API_VERSION_1_4)
    }

    @Test
    fun `enumerate instance extensions`() {
        val extensions = vulkan.enumerateInstanceExtensionProperties()

        assertTrue(extensions.isNotEmpty())
        assertTrue(extensions.all { it.name.startsWith("VK_") && it.specVersion > 0u })
    }

    @Test
    fun `enumerate instance extensions provided by a layer`() {
        val layers = vulkan.enumerateInstanceLayerProperties()

        layers.forEach { layer ->
            val extensions = vulkan.enumerateInstanceExtensionProperties(layer.name)
            assertTrue(extensions.all { it.name.startsWith("VK_") })
        }
    }

    @Test
    fun `enumerate instance layers`() {
        val layers = vulkan.enumerateInstanceLayerProperties()

        assertTrue(layers.isNotEmpty())
        assertTrue(layers.all { it.name.startsWith("VK_LAYER_") && it.specVersion > 0u })
    }

    @Test
    fun `buffer and image memory requirements include dedicated allocation flags`() {
        vulkan.withTestDevice { _, device ->
            val buffer = device.createBuffer {
                size = 256uL
                usage = VK_BUFFER_USAGE_VERTEX_BUFFER_BIT
                sharingMode = VK_SHARING_MODE_EXCLUSIVE
            }
            buffer.use {
                val requirements = it.getMemoryRequirements()
                assertTrue(requirements.size >= 256uL)
                assertTrue(requirements.alignment > 0uL)
                assertTrue(requirements.memoryTypeBits != 0u)
                assertFalse(requirements.requiresDedicatedAllocation)
            }

            val image = device.createImage {
                imageType = VK_IMAGE_TYPE_2D
                format = VK_FORMAT_R8G8B8A8_UNORM
                extent.width = 64u
                extent.height = 64u
                extent.depth = 1u
                mipLevels = 1u
                arrayLayers = 1u
                samples = VK_SAMPLE_COUNT_1_BIT
                tiling = VK_IMAGE_TILING_OPTIMAL
                usage = VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT
                sharingMode = VK_SHARING_MODE_EXCLUSIVE
                initialLayout = VK_IMAGE_LAYOUT_UNDEFINED
            }
            image.use {
                val requirements = it.getMemoryRequirements()
                assertTrue(requirements.size > 0uL)
                assertTrue(requirements.alignment > 0uL)
                assertTrue(requirements.memoryTypeBits != 0u)
                assertTrue(!requirements.requiresDedicatedAllocation || requirements.prefersDedicatedAllocation)
            }
        }
    }

    @Test
    fun `memory budget query returns one entry per memory heap`() {
        vulkan.createTestInstance().use { instance ->
            val physicalDevice = instance.enumeratePhysicalDevices()
                .firstOrNull { device ->
                    memScoped { device.getProperties().apiVersion >= VK_API_VERSION_1_4 } &&
                        device.enumerateDeviceExtensionProperties().any { it.name == VK_EXT_MEMORY_BUDGET_EXTENSION_NAME }
                }
                ?: return
            val memoryProperties = physicalDevice.getMemoryProperties()
            val memoryBudgets = physicalDevice.getMemoryBudget()

            assertEquals(memoryProperties.memoryHeaps.size, memoryBudgets.size)
            assertTrue(memoryBudgets.any { it.budget > 0uL })
        }
    }

    @Test
    fun `object-less buffer and image memory requirements match created objects`() {
        vulkan.withTestDevice { _, device ->
            val queriedBufferRequirements = device.getDeviceBufferMemoryRequirements {
                size = 256uL
                usage = VK_BUFFER_USAGE_VERTEX_BUFFER_BIT
                sharingMode = VK_SHARING_MODE_EXCLUSIVE
            }
            val bufferRequirements = device.createBuffer {
                size = 256uL
                usage = VK_BUFFER_USAGE_VERTEX_BUFFER_BIT
                sharingMode = VK_SHARING_MODE_EXCLUSIVE
            }.use { it.getMemoryRequirements() }
            assertEquals(bufferRequirements, queriedBufferRequirements)

            val queriedImageRequirements = device.getDeviceImageMemoryRequirements {
                imageType = VK_IMAGE_TYPE_2D
                format = VK_FORMAT_R8G8B8A8_UNORM
                extent.width = 64u
                extent.height = 64u
                extent.depth = 1u
                mipLevels = 1u
                arrayLayers = 1u
                samples = VK_SAMPLE_COUNT_1_BIT
                tiling = VK_IMAGE_TILING_OPTIMAL
                usage = VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT
                sharingMode = VK_SHARING_MODE_EXCLUSIVE
                initialLayout = VK_IMAGE_LAYOUT_UNDEFINED
            }
            val imageRequirements = device.createImage {
                imageType = VK_IMAGE_TYPE_2D
                format = VK_FORMAT_R8G8B8A8_UNORM
                extent.width = 64u
                extent.height = 64u
                extent.depth = 1u
                mipLevels = 1u
                arrayLayers = 1u
                samples = VK_SAMPLE_COUNT_1_BIT
                tiling = VK_IMAGE_TILING_OPTIMAL
                usage = VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT
                sharingMode = VK_SHARING_MODE_EXCLUSIVE
                initialLayout = VK_IMAGE_LAYOUT_UNDEFINED
            }.use { it.getMemoryRequirements() }
            assertEquals(imageRequirements, queriedImageRequirements)
        }
    }

    @Test
    fun `device memory commitment query succeeds on lazily allocated memory`() {
        vulkan.createTestInstance().use { instance ->
            val match = instance.enumeratePhysicalDevices()
                .filter { memScoped { it.getProperties().apiVersion >= VK_API_VERSION_1_4 } }
                .firstNotNullOfOrNull { physicalDevice ->
                    val typeIndex = physicalDevice.getMemoryProperties().memoryTypes
                        .indexOfFirst { (it.propertyFlags and VK_MEMORY_PROPERTY_LAZILY_ALLOCATED_BIT) != 0u }
                    if (typeIndex >= 0) physicalDevice to typeIndex.toUInt() else null
                }
                ?: return
            val (physicalDevice, lazyTypeIndex) = match
            createTestDevice(physicalDevice)?.use { device ->
                device.allocateMemory {
                    allocationSize = 4096uL
                    memoryTypeIndex = lazyTypeIndex
                }.use { memory ->
                    assertTrue(memory.getCommitment() <= memory.size)
                }
            }
        }
    }
}
