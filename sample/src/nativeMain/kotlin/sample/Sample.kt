package sample

import io.technoirlab.volk.VK_EXT_LAYER_SETTINGS_EXTENSION_NAME
import io.technoirlab.volk.VK_EXT_MEMORY_BUDGET_EXTENSION_NAME
import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME
import io.technoirlab.volk.VK_LAYER_SETTING_TYPE_BOOL32_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_LAYER_SETTINGS_CREATE_INFO_EXT
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VK_VERSION_MAJOR
import io.technoirlab.volk.VK_VERSION_MINOR
import io.technoirlab.volk.VK_VERSION_PATCH
import io.technoirlab.volk.VkBool32Var
import io.technoirlab.volk.VkDeviceQueueCreateInfo
import io.technoirlab.volk.VkLayerSettingEXT
import io.technoirlab.volk.VkLayerSettingsCreateInfoEXT
import io.technoirlab.vulkan.ApplicationInfo
import io.technoirlab.vulkan.ExtensionProperties
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.Vulkan
import io.technoirlab.vulkan.device.Device
import io.technoirlab.vulkan.device.PhysicalDevice
import kotlinx.cinterop.AutofreeScope
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKString
import kotlinx.cinterop.value
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
class Sample : AutoCloseable {
    private val vulkan = Vulkan()
    private var instance: Instance? = null
    private var device: Device? = null

    fun run() {
        val vulkanVersion = vulkan.instanceVersion
        println("Vulkan version: ${VK_VERSION_MAJOR(vulkanVersion)}.${VK_VERSION_MINOR(vulkanVersion)}.${VK_VERSION_PATCH(vulkanVersion)}")

        val instance = createInstance().also { instance = it }
        println("Created Vulkan instance")

        val physicalDevices = instance.enumeratePhysicalDevices().map { physicalDevice ->
            physicalDevice to memScoped { physicalDevice.getProperties().deviceName.toKString() }
        }
        println("Supported physical devices: ${physicalDevices.joinToString(", ") { it.second }}")

        val (physicalDevice, deviceName) = physicalDevices.firstOrNull() ?: error("No physical devices found")

        val deviceExtensions = physicalDevice.enumerateDeviceExtensionProperties()
        println("Supported device extensions: ${deviceExtensions.joinToString(", ") { it.name }}")

        if (deviceExtensions.any { it.name == VK_EXT_MEMORY_BUDGET_EXTENSION_NAME }) {
            val memoryBudgets = physicalDevice.getMemoryBudget()
            println("Memory heap budgets:")
            memoryBudgets.forEachIndexed { index, heap ->
                println("  Heap $index: usage=${heap.usage} bytes, budget=${heap.budget} bytes")
            }
        }

        device = createDevice(physicalDevice, deviceExtensions)
        println("Created logical device for $deviceName")
    }

    override fun close() {
        device?.close()
        instance?.close()
        vulkan.close()
    }

    private fun createDevice(physicalDevice: PhysicalDevice, supportedExtensions: List<ExtensionProperties>): Device {
        val enabledExtensions = buildList {
            if (supportedExtensions.any { it.name == VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME }) {
                add(VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME)
            }
        }
        val queueFamilyIndex = physicalDevice.getQueueFamilyProperties()
            .indexOfFirst { it.queueCount > 0u }
            .takeIf { it >= 0 }
            ?.toUInt()
            ?: error("No queue families found")

        return memScoped {
            val queuePriority = alloc<FloatVar> { value = 1.0f }
            val queueCreateInfo = alloc<VkDeviceQueueCreateInfo> {
                sType = VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO
                this.queueFamilyIndex = queueFamilyIndex
                queueCount = 1u
                pQueuePriorities = queuePriority.ptr
            }
            physicalDevice.createDevice(
                enabledExtensions = enabledExtensions,
                createInfo = {
                    queueCreateInfoCount = 1u
                    pQueueCreateInfos = queueCreateInfo.ptr
                },
            )
        }
    }

    private fun createInstance(): Instance {
        val instanceExtensions = vulkan.enumerateInstanceExtensionProperties()
        println("Supported instance extensions: ${instanceExtensions.joinToString(", ") { it.name }}")

        val instanceLayers = vulkan.enumerateInstanceLayerProperties()
        println("Supported instance layers: ${instanceLayers.joinToString(", ") { it.name }}")

        val hasValidationLayer = instanceLayers.any { it.name == VALIDATION_LAYER_NAME }
        val hasLayerSettings = hasValidationLayer &&
            vulkan.enumerateInstanceExtensionProperties(VALIDATION_LAYER_NAME).any { it.name == VK_EXT_LAYER_SETTINGS_EXTENSION_NAME }
        val enabledLayers = buildList {
            if (hasValidationLayer) {
                add(VALIDATION_LAYER_NAME)
            }
        }
        val enabledExtensions = buildList {
            if (Platform.osFamily.isAppleFamily) {
                add(VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)
            }
            if (hasLayerSettings) {
                add(VK_EXT_LAYER_SETTINGS_EXTENSION_NAME)
            }
        }

        return memScoped {
            val layerSettings = if (hasLayerSettings) createSyncValidationSettings() else null
            vulkan.createInstance(
                applicationInfo = ApplicationInfo(),
                enabledLayers = enabledLayers,
                enabledExtensions = enabledExtensions,
                extensions = layerSettings?.ptr,
            )
        }
    }

    context(allocator: AutofreeScope)
    private fun createSyncValidationSettings(): VkLayerSettingsCreateInfoEXT {
        val validateSync = allocator.alloc<VkBool32Var> { value = VK_TRUE }
        val layerSetting = allocator.alloc<VkLayerSettingEXT> {
            pLayerName = VALIDATION_LAYER_NAME.cstr.getPointer(allocator)
            pSettingName = "validate_sync".cstr.getPointer(allocator)
            type = VK_LAYER_SETTING_TYPE_BOOL32_EXT
            valueCount = 1u
            pValues = validateSync.ptr
        }
        return allocator.alloc<VkLayerSettingsCreateInfoEXT> {
            sType = VK_STRUCTURE_TYPE_LAYER_SETTINGS_CREATE_INFO_EXT
            settingCount = 1u
            pSettings = layerSetting.ptr
        }
    }

    private val OsFamily.isAppleFamily: Boolean
        get() = when (this) {
            OsFamily.MACOSX, OsFamily.IOS, OsFamily.TVOS, OsFamily.WATCHOS -> true
            else -> false
        }

    companion object {
        private const val VALIDATION_LAYER_NAME = "VK_LAYER_KHRONOS_validation"
    }
}
