package io.technoirlab.vulkan

import io.technoirlab.volk.VK_API_VERSION_1_4
import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO
import io.technoirlab.volk.VkDeviceQueueCreateInfo
import io.technoirlab.vulkan.device.Device
import io.technoirlab.vulkan.device.PhysicalDevice
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

internal fun createTestDevice(physicalDevice: PhysicalDevice): Device? {
    val deviceExtensions = physicalDevice.enumerateDeviceExtensionProperties()
    val enabledExtensions = buildList {
        if (deviceExtensions.any { it.name == VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME }) {
            add(VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME)
        }
    }
    val queueFamilyIndex = physicalDevice.getQueueFamilyProperties()
        .indexOfFirst { it.queueCount > 0u }
        .takeIf { it >= 0 }
        ?.toUInt()
        ?: return null
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

internal fun Vulkan.createTestInstance(): Instance {
    val hasPortabilityExtension = enumerateInstanceExtensionProperties().any {
        it.name == VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
    }
    val extensions = if (hasPortabilityExtension) {
        listOf(VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)
    } else {
        emptyList()
    }
    return createInstance(ApplicationInfo(), enabledExtensions = extensions)
}

/**
 * Runs [block] with a device created on the first Vulkan 1.4 physical device, or skips it if there is none.
 */
internal fun Vulkan.withTestDevice(block: (PhysicalDevice, Device) -> Unit) {
    createTestInstance().use { instance ->
        val physicalDevice = instance.enumeratePhysicalDevices()
            .firstOrNull { memScoped { it.getProperties().apiVersion >= VK_API_VERSION_1_4 } }
            ?: return
        val device = createTestDevice(physicalDevice) ?: return
        device.use { block(physicalDevice, it) }
    }
}
