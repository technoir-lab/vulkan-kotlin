package sample

import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO
import io.technoirlab.volk.VK_VERSION_MAJOR
import io.technoirlab.volk.VK_VERSION_MINOR
import io.technoirlab.volk.VK_VERSION_PATCH
import io.technoirlab.volk.VkDeviceQueueCreateInfo
import io.technoirlab.vulkan.ApplicationInfo
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.Vulkan
import io.technoirlab.vulkan.device.Device
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.alloc
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

        val instanceExtensions = vulkan.enumerateInstanceExtensionProperties()
        println("Supported instance extensions: ${instanceExtensions.joinToString(", ") { it.name }}")

        val instanceLayers = vulkan.enumerateInstanceLayerProperties()
        println("Supported instance layers: ${instanceLayers.joinToString(", ") { it.name }}")

        val extensions = buildList {
            if (Platform.osFamily.isAppleFamily) {
                add(VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)
            }
        }

        val applicationInfo = ApplicationInfo()
        val instance = vulkan.createInstance(applicationInfo, enabledExtensions = extensions).also { instance = it }
        println("Created Vulkan instance")

        val physicalDevices = instance.enumeratePhysicalDevices().map { physicalDevice ->
            physicalDevice to memScoped { physicalDevice.getProperties().deviceName.toKString() }
        }
        println("Supported physical devices: ${physicalDevices.joinToString(", ") { it.second }}")

        val (physicalDevice, deviceName) = physicalDevices.firstOrNull() ?: error("No physical devices found")

        val deviceExtensions = physicalDevice.enumerateDeviceExtensionProperties()
        println("Supported device extensions: ${deviceExtensions.joinToString(", ") { it.name }}")

        val enabledDeviceExtensions = buildList {
            if (deviceExtensions.any { it.name == VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME }) {
                add(VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME)
            }
        }
        val queueFamilyIndex = physicalDevice.getQueueFamilyProperties()
            .indexOfFirst { it.queueCount > 0u }
            .takeIf { it >= 0 }
            ?.toUInt()
            ?: error("No queue families found")

        device = memScoped {
            val queuePriority = alloc<FloatVar> { value = 1.0f }
            val queueCreateInfo = alloc<VkDeviceQueueCreateInfo> {
                sType = VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO
                this.queueFamilyIndex = queueFamilyIndex
                queueCount = 1u
                pQueuePriorities = queuePriority.ptr
            }
            physicalDevice.createDevice(
                enabledExtensions = enabledDeviceExtensions,
                createInfo = {
                    queueCreateInfoCount = 1u
                    pQueueCreateInfos = queueCreateInfo.ptr
                },
            )
        }
        println("Created logical device for $deviceName")
    }

    override fun close() {
        device?.close()
        instance?.close()
        vulkan.close()
    }

    private val OsFamily.isAppleFamily: Boolean
        get() = when (this) {
            OsFamily.MACOSX, OsFamily.IOS, OsFamily.TVOS, OsFamily.WATCHOS -> true
            else -> false
        }
}
