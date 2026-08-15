package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_PHYSICAL_DEVICE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_FORMAT_PROPERTIES_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_FORMAT_PROPERTIES_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_IMAGE_FORMAT_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_PROPERTIES_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_PROPERTIES
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_PROPERTIES
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_PROPERTIES
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_4_FEATURES
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_4_PROPERTIES
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkBool32Var
import io.technoirlab.volk.VkDeviceCreateInfo
import io.technoirlab.volk.VkDeviceVar
import io.technoirlab.volk.VkExtensionProperties
import io.technoirlab.volk.VkFormat
import io.technoirlab.volk.VkFormatProperties2
import io.technoirlab.volk.VkImageFormatProperties2
import io.technoirlab.volk.VkImageLayoutVar
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPhysicalDevice
import io.technoirlab.volk.VkPhysicalDeviceFeatures
import io.technoirlab.volk.VkPhysicalDeviceFeatures2
import io.technoirlab.volk.VkPhysicalDeviceImageFormatInfo2
import io.technoirlab.volk.VkPhysicalDeviceMemoryProperties
import io.technoirlab.volk.VkPhysicalDeviceProperties
import io.technoirlab.volk.VkPhysicalDeviceProperties2
import io.technoirlab.volk.VkPhysicalDeviceVulkan11Features
import io.technoirlab.volk.VkPhysicalDeviceVulkan11Properties
import io.technoirlab.volk.VkPhysicalDeviceVulkan12Features
import io.technoirlab.volk.VkPhysicalDeviceVulkan12Properties
import io.technoirlab.volk.VkPhysicalDeviceVulkan13Features
import io.technoirlab.volk.VkPhysicalDeviceVulkan13Properties
import io.technoirlab.volk.VkPhysicalDeviceVulkan14Features
import io.technoirlab.volk.VkPhysicalDeviceVulkan14Properties
import io.technoirlab.volk.VkPresentModeKHR
import io.technoirlab.volk.VkPresentModeKHRVar
import io.technoirlab.volk.VkQueueFamilyProperties
import io.technoirlab.volk.VkSurfaceCapabilitiesKHR
import io.technoirlab.volk.VkSurfaceFormatKHR
import io.technoirlab.volk.vkCreateDevice
import io.technoirlab.volk.vkEnumerateDeviceExtensionProperties
import io.technoirlab.volk.vkGetPhysicalDeviceFeatures2
import io.technoirlab.volk.vkGetPhysicalDeviceFormatProperties2
import io.technoirlab.volk.vkGetPhysicalDeviceImageFormatProperties2
import io.technoirlab.volk.vkGetPhysicalDeviceMemoryProperties
import io.technoirlab.volk.vkGetPhysicalDeviceProperties
import io.technoirlab.volk.vkGetPhysicalDeviceProperties2
import io.technoirlab.volk.vkGetPhysicalDeviceQueueFamilyProperties
import io.technoirlab.volk.vkGetPhysicalDeviceSurfaceCapabilitiesKHR
import io.technoirlab.volk.vkGetPhysicalDeviceSurfaceFormatsKHR
import io.technoirlab.volk.vkGetPhysicalDeviceSurfacePresentModesKHR
import io.technoirlab.volk.vkGetPhysicalDeviceSurfaceSupportKHR
import io.technoirlab.vulkan.presentation.Surface
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Wrapper for [VkPhysicalDevice].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPhysicalDevice.html">VkPhysicalDevice Manual Page</a>
 */
class PhysicalDevice internal constructor(
    override val handle: VkPhysicalDevice,
) : VulkanObject {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_PHYSICAL_DEVICE

    /**
     * Create a new device instance.
     *
     * @param createInfo Configures device creation.
     * @param features Configures enabled Vulkan 1.0 features.
     * @param features11 Configures enabled Vulkan 1.1 features.
     * @param features12 Configures enabled Vulkan 1.2 features.
     * @param features13 Configures enabled Vulkan 1.3 features.
     * @param features14 Configures enabled Vulkan 1.4 features.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDevice.html">vkCreateDevice Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun createDevice(
        createInfo: VkDeviceCreateInfo.() -> Unit = {},
        features: VkPhysicalDeviceFeatures.() -> Unit = {},
        features11: VkPhysicalDeviceVulkan11Features.() -> Unit = {},
        features12: VkPhysicalDeviceVulkan12Features.() -> Unit = {},
        features13: VkPhysicalDeviceVulkan13Features.() -> Unit = {},
        features14: VkPhysicalDeviceVulkan14Features.() -> Unit = {},
    ): Device {
        val features14 = allocator.alloc<VkPhysicalDeviceVulkan14Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_4_FEATURES
            features14()
        }
        val features13 = allocator.alloc<VkPhysicalDeviceVulkan13Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES
            features13()
            pNext = features14.ptr
        }
        val features12 = allocator.alloc<VkPhysicalDeviceVulkan12Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES
            features12()
            pNext = features13.ptr
        }
        val features11 = allocator.alloc<VkPhysicalDeviceVulkan11Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES
            features11()
            pNext = features12.ptr
        }
        val features = allocator.alloc<VkPhysicalDeviceFeatures2> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2
            this.features.features()
            pNext = features11.ptr
        }
        val deviceCreateInfo = allocator.alloc<VkDeviceCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO
            createInfo()
            pNext = features.ptr
        }
        val deviceVar = allocator.alloc<VkDeviceVar>()
        vkCreateDevice!!(handle, deviceCreateInfo.ptr, null, deviceVar.ptr)
            .checkResult("Failed to create a device")
        return Device(deviceVar.value!!)
    }

    /**
     * List the extensions supported by the device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateDeviceExtensionProperties.html">vkEnumerateDeviceExtensionProperties Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun enumerateDeviceExtensionProperties(): List<VkExtensionProperties> {
        val countVar = allocator.alloc<UIntVar>()
        vkEnumerateDeviceExtensionProperties!!(handle, null, countVar.ptr, null)

        val count = countVar.value.toLong()
        if (count == 0L) return emptyList()

        val extensionProperties = allocator.allocArray<VkExtensionProperties>(count)
        vkEnumerateDeviceExtensionProperties!!(handle, null, countVar.ptr, extensionProperties)

        return (0 until count).map { extensionProperties[it] }
    }

    /**
     * Retrieve the fine-grained features that can be supported by the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceFeatures2.html">vkGetPhysicalDeviceFeatures2 Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getFeatures(extensionFeatures: COpaquePointer? = null): Features {
        val features14 = allocator.alloc<VkPhysicalDeviceVulkan14Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_4_FEATURES
            pNext = extensionFeatures
        }
        val features13 = allocator.alloc<VkPhysicalDeviceVulkan13Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_FEATURES
            pNext = features14.ptr
        }
        val features12 = allocator.alloc<VkPhysicalDeviceVulkan12Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_FEATURES
            pNext = features13.ptr
        }
        val features11 = allocator.alloc<VkPhysicalDeviceVulkan11Features> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_FEATURES
            pNext = features12.ptr
        }
        val features = allocator.alloc<VkPhysicalDeviceFeatures2> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_FEATURES_2
            pNext = features11.ptr
        }
        vkGetPhysicalDeviceFeatures2!!(handle, features.ptr)
        return Features(features, features11, features12, features13, features14)
    }

    /**
     * Retrieve properties of a format supported by the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceFormatProperties2.html">vkGetPhysicalDeviceFormatProperties2 Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getFormatProperties(format: VkFormat): VkFormatProperties2 {
        val properties = allocator.alloc<VkFormatProperties2> {
            sType = VK_STRUCTURE_TYPE_FORMAT_PROPERTIES_2
        }
        vkGetPhysicalDeviceFormatProperties2!!(handle, format, properties.ptr)
        return properties
    }

    /**
     * Retrieve properties of an image format applied to a particular type of image resource.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceImageFormatProperties2.html">vkGetPhysicalDeviceImageFormatProperties2 Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getImageFormatProperties(formatInfo: VkPhysicalDeviceImageFormatInfo2.() -> Unit): VkImageFormatProperties2 {
        val imageFormatInfo = allocator.alloc<VkPhysicalDeviceImageFormatInfo2> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_IMAGE_FORMAT_INFO_2
            formatInfo()
        }
        val properties = allocator.alloc<VkImageFormatProperties2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_FORMAT_PROPERTIES_2
        }
        vkGetPhysicalDeviceImageFormatProperties2!!(handle, imageFormatInfo.ptr, properties.ptr)
            .checkResult("Failed to get image format properties")
        return properties
    }

    /**
     * Retrieve properties of the physical device's memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceMemoryProperties.html">vkGetPhysicalDeviceMemoryProperties Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getMemoryProperties(): VkPhysicalDeviceMemoryProperties {
        val memoryProperties = allocator.alloc<VkPhysicalDeviceMemoryProperties>()
        vkGetPhysicalDeviceMemoryProperties!!(handle, memoryProperties.ptr)
        return memoryProperties
    }

    /**
     * Retrieve properties of the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceProperties.html">vkGetPhysicalDeviceProperties Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getProperties(): VkPhysicalDeviceProperties {
        val properties = allocator.alloc<VkPhysicalDeviceProperties>()
        vkGetPhysicalDeviceProperties!!(handle, properties.ptr)
        return properties
    }

    /**
     * Retrieve properties of the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceProperties2.html">vkGetPhysicalDeviceProperties2 Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getProperties2(extensionProperties: COpaquePointer? = null): Properties {
        val properties14 = allocator.alloc<VkPhysicalDeviceVulkan14Properties> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_4_PROPERTIES
            pNext = extensionProperties
        }
        val properties13 = allocator.alloc<VkPhysicalDeviceVulkan13Properties> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_3_PROPERTIES
            pNext = properties14.ptr
        }
        val properties12 = allocator.alloc<VkPhysicalDeviceVulkan12Properties> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_2_PROPERTIES
            pNext = properties13.ptr
        }
        val properties11 = allocator.alloc<VkPhysicalDeviceVulkan11Properties> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_VULKAN_1_1_PROPERTIES
            pNext = properties12.ptr
        }
        val properties = allocator.alloc<VkPhysicalDeviceProperties2> {
            sType = VK_STRUCTURE_TYPE_PHYSICAL_DEVICE_PROPERTIES_2
            pNext = properties11.ptr
        }
        vkGetPhysicalDeviceProperties2!!(handle, properties.ptr)
        if (properties14.copySrcLayoutCount > 0u) {
            properties14.pCopySrcLayouts = allocator.allocArray<VkImageLayoutVar>(properties14.copySrcLayoutCount.toLong())
        }
        if (properties14.copyDstLayoutCount > 0u) {
            properties14.pCopyDstLayouts = allocator.allocArray<VkImageLayoutVar>(properties14.copyDstLayoutCount.toLong())
        }
        vkGetPhysicalDeviceProperties2!!(handle, properties.ptr)
        return Properties(properties, properties11, properties12, properties13, properties14)
    }

    /**
     * Retrieve properties of the queues available on the physical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceQueueFamilyProperties.html">vkGetPhysicalDeviceQueueFamilyProperties Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getQueueFamilyProperties(): List<VkQueueFamilyProperties> {
        val countVar = allocator.alloc<UIntVar>()
        vkGetPhysicalDeviceQueueFamilyProperties!!(handle, countVar.ptr, null)

        val count = countVar.value.toLong()
        if (count == 0L) return emptyList()

        val queueFamilyProperties = allocator.allocArray<VkQueueFamilyProperties>(count)
        vkGetPhysicalDeviceQueueFamilyProperties!!(handle, countVar.ptr, queueFamilyProperties)

        return (0 until count).map { queueFamilyProperties[it] }
    }

    /**
     * Query the basic capabilities of a surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceSurfaceCapabilitiesKHR.html">vkGetPhysicalDeviceSurfaceCapabilitiesKHR Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getSurfaceCapabilities(surface: Surface): VkSurfaceCapabilitiesKHR {
        val capabilities = allocator.alloc<VkSurfaceCapabilitiesKHR>()
        vkGetPhysicalDeviceSurfaceCapabilitiesKHR!!(handle, surface.handle, capabilities.ptr)
            .checkResult("Failed to get surface capabilities")
        return capabilities
    }

    /**
     * Query color formats supported by a surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceSurfaceFormatsKHR.html">vkGetPhysicalDeviceSurfaceFormatsKHR Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getSurfaceFormats(surface: Surface): List<VkSurfaceFormatKHR> {
        val countVar = allocator.alloc<UIntVar>()
        vkGetPhysicalDeviceSurfaceFormatsKHR!!(handle, surface.handle, countVar.ptr, null)
            .checkResult("Failed to get surface formats")

        val count = countVar.value.toLong()
        if (count == 0L) return emptyList()

        val surfaceFormats = allocator.allocArray<VkSurfaceFormatKHR>(count)
        vkGetPhysicalDeviceSurfaceFormatsKHR!!(handle, surface.handle, countVar.ptr, surfaceFormats)
            .checkResult("Failed to get surface formats")

        return (0 until count).map { surfaceFormats[it] }
    }

    /**
     * Query supported presentation modes for a surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceSurfacePresentModesKHR.html">vkGetPhysicalDeviceSurfacePresentModesKHR Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getSurfacePresentModes(surface: Surface): Set<VkPresentModeKHR> {
        val countVar = allocator.alloc<UIntVar>()
        vkGetPhysicalDeviceSurfacePresentModesKHR!!(handle, surface.handle, countVar.ptr, null)
            .checkResult("Failed to get surface present modes")

        val count = countVar.value.toLong()
        if (count == 0L) return emptySet()

        val presentModes = allocator.allocArray<VkPresentModeKHRVar>(count)
        vkGetPhysicalDeviceSurfacePresentModesKHR!!(handle, surface.handle, countVar.ptr, presentModes)
            .checkResult("Failed to get surface present modes")

        return (0 until count).mapTo(mutableSetOf()) { presentModes[it] }
    }

    /**
     * Query if presentation is supported.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPhysicalDeviceSurfaceSupportKHR.html">vkGetPhysicalDeviceSurfaceSupportKHR Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getSurfaceSupport(surface: Surface, queueFamilyIndex: UInt): Boolean {
        val isSupported = allocator.alloc<VkBool32Var>()
        vkGetPhysicalDeviceSurfaceSupportKHR!!(handle, queueFamilyIndex, surface.handle, isSupported.ptr)
            .checkResult("Failed to check surface support")
        return isSupported.value == VK_TRUE
    }

    /**
     * Core physical-device features returned by [getFeatures].
     *
     * @property features Vulkan 1.0 features.
     * @property features11 Vulkan 1.1 features.
     * @property features12 Vulkan 1.2 features.
     * @property features13 Vulkan 1.3 features.
     * @property features14 Vulkan 1.4 features.
     */
    data class Features(
        val features: VkPhysicalDeviceFeatures2,
        val features11: VkPhysicalDeviceVulkan11Features,
        val features12: VkPhysicalDeviceVulkan12Features,
        val features13: VkPhysicalDeviceVulkan13Features,
        val features14: VkPhysicalDeviceVulkan14Features,
    )

    /**
     * Core physical-device properties returned by [getProperties2].
     *
     * @property properties Vulkan 1.0 properties.
     * @property properties11 Vulkan 1.1 properties.
     * @property properties12 Vulkan 1.2 properties.
     * @property properties13 Vulkan 1.3 properties.
     * @property properties14 Vulkan 1.4 properties.
     */
    data class Properties(
        val properties: VkPhysicalDeviceProperties2,
        val properties11: VkPhysicalDeviceVulkan11Properties,
        val properties12: VkPhysicalDeviceVulkan12Properties,
        val properties13: VkPhysicalDeviceVulkan13Properties,
        val properties14: VkPhysicalDeviceVulkan14Properties,
    )
}
