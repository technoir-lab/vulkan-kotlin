package io.technoirlab.vulkan

import io.technoirlab.volk.VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VK_STRUCTURE_TYPE_APPLICATION_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
import io.technoirlab.volk.VkApplicationInfo
import io.technoirlab.volk.VkExtensionProperties
import io.technoirlab.volk.VkInstanceCreateInfo
import io.technoirlab.volk.VkInstanceVar
import io.technoirlab.volk.VkLayerProperties
import io.technoirlab.volk.vkCreateInstance
import io.technoirlab.volk.vkEnumerateInstanceExtensionProperties
import io.technoirlab.volk.vkEnumerateInstanceLayerProperties
import io.technoirlab.volk.volkFinalize
import io.technoirlab.volk.volkGetInstanceVersion
import io.technoirlab.volk.volkInitialize
import kotlinx.cinterop.AutofreeScope
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cstr
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toCStringArray
import kotlinx.cinterop.value

/**
 * Vulkan instance loader.
 */
class Vulkan : AutoCloseable {
    init {
        volkInitialize().checkResult("Failed to initialize Volk")
    }

    /**
     * Get the Vulkan instance version.
     */
    val instanceVersion: UInt
        get() = volkGetInstanceVersion()

    /**
     * Unload Vulkan.
     */
    override fun close() {
        volkFinalize()
    }

    /**
     * Create a new Vulkan instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateInstance.html">vkCreateInstance Manual Page</a>
     */
    context(allocator: AutofreeScope)
    fun createInstance(
        enabledLayers: List<String> = emptyList(),
        enabledExtensions: List<String> = emptyList(),
        applicationInfo: ApplicationInfo? = null
    ): Instance {
        val instanceCreateInfo = allocator.alloc<VkInstanceCreateInfo> {
            sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
            pApplicationInfo = applicationInfo?.toVkApplicationInfo()?.ptr
            flags = if (VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME in enabledExtensions) {
                VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
            } else {
                0u
            }
            if (enabledLayers.isNotEmpty()) {
                enabledLayerCount = enabledLayers.size.toUInt()
                ppEnabledLayerNames = enabledLayers.toCStringArray(allocator)
            }
            if (enabledExtensions.isNotEmpty()) {
                enabledExtensionCount = enabledExtensions.size.toUInt()
                ppEnabledExtensionNames = enabledExtensions.toCStringArray(allocator)
            }
        }
        val instanceVar = allocator.alloc<VkInstanceVar>()
        vkCreateInstance!!(instanceCreateInfo.ptr, null, instanceVar.ptr)
            .checkResult("Failed to create a Vulkan instance")
        return Instance(instanceVar.value!!)
    }

    /**
     * List global extension properties.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateInstanceExtensionProperties.html">vkEnumerateInstanceExtensionProperties Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun enumerateInstanceExtensionProperties(): List<VkExtensionProperties> {
        val countVar = allocator.alloc<UIntVar>()
        vkEnumerateInstanceExtensionProperties!!(null, countVar.ptr, null)
            .checkResult("Failed to enumerate instance extensions")

        val count = countVar.value.toLong()
        if (count == 0L) return emptyList()

        val extensionProperties = allocator.allocArray<VkExtensionProperties>(count)
        vkEnumerateInstanceExtensionProperties!!(null, countVar.ptr, extensionProperties)
            .checkResult("Failed to enumerate instance extensions")

        return (0 until count).map { extensionProperties[it] }
    }

    /**
     * List global layer properties.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateInstanceLayerProperties.html">vkEnumerateInstanceLayerProperties Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun enumerateInstanceLayerProperties(): List<VkLayerProperties> {
        val countVar = allocator.alloc<UIntVar>()
        vkEnumerateInstanceLayerProperties!!(countVar.ptr, null)
            .checkResult("Failed to enumerate instance layers")

        val count = countVar.value.toLong()
        if (count == 0L) return emptyList()

        val layerProperties = allocator.allocArray<VkLayerProperties>(count)
        vkEnumerateInstanceLayerProperties!!(countVar.ptr, layerProperties)
            .checkResult("Failed to enumerate instance layers")

        return (0 until count).map { layerProperties[it] }
    }

    context(allocator: AutofreeScope)
    private fun ApplicationInfo.toVkApplicationInfo(): VkApplicationInfo =
        allocator.alloc {
            sType = VK_STRUCTURE_TYPE_APPLICATION_INFO
            apiVersion = this@toVkApplicationInfo.apiVersion
            pApplicationName = this@toVkApplicationInfo.applicationName?.cstr?.getPointer(allocator)
            applicationVersion = this@toVkApplicationInfo.applicationVersion
            pEngineName = this@toVkApplicationInfo.engineName?.cstr?.getPointer(allocator)
            engineVersion = this@toVkApplicationInfo.engineVersion
        }
}
