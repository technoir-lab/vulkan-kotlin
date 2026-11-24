package io.technoirlab.vulkan

import io.technoirlab.volk.VK_INCOMPLETE
import io.technoirlab.volk.VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VK_STRUCTURE_TYPE_APPLICATION_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
import io.technoirlab.volk.VkApplicationInfo
import io.technoirlab.volk.VkExtensionProperties
import io.technoirlab.volk.VkInstanceCreateInfo
import io.technoirlab.volk.VkInstanceVar
import io.technoirlab.volk.VkLayerProperties
import io.technoirlab.volk.VkResult
import io.technoirlab.volk.vkCreateInstance
import io.technoirlab.volk.vkEnumerateInstanceExtensionProperties
import io.technoirlab.volk.vkEnumerateInstanceLayerProperties
import io.technoirlab.volk.volkFinalize
import io.technoirlab.volk.volkGetInstanceVersion
import io.technoirlab.volk.volkInitialize
import io.technoirlab.volk.volkKotlinInitialize
import kotlinx.cinterop.AutofreeScope
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cstr
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toCStringArray
import kotlinx.cinterop.toKString
import kotlinx.cinterop.value

/**
 * Vulkan instance loader.
 */
class Vulkan : AutoCloseable {
    init {
        volkKotlinInitialize()
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
     * If `VK_KHR_portability_enumeration` is included in [enabledExtensions],
     * `VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR` is set.
     *
     * @param applicationInfo Application and engine metadata.
     * @param enabledLayers Names of the instance layers to enable.
     * @param enabledExtensions Names of the instance extensions to enable.
     * @param extensions Optional `pNext` chain of extension structures to attach to `VkInstanceCreateInfo`, such as
     * `VkLayerSettingsCreateInfoEXT` or `VkValidationFeaturesEXT`. The chained structures and any memory they
     * reference must remain valid until this call returns.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateInstance.html">vkCreateInstance Manual Page</a>
     */
    fun createInstance(
        applicationInfo: ApplicationInfo,
        enabledLayers: List<String> = emptyList(),
        enabledExtensions: List<String> = emptyList(),
        extensions: COpaquePointer? = null,
    ): Instance = memScoped {
        val instanceCreateInfo = alloc<VkInstanceCreateInfo> {
            sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO
            pNext = extensions
            pApplicationInfo = applicationInfo.toVkApplicationInfo().ptr
            flags = if (VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME in enabledExtensions) {
                VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
            } else {
                0u
            }
            if (enabledLayers.isNotEmpty()) {
                enabledLayerCount = enabledLayers.size.toUInt()
                ppEnabledLayerNames = enabledLayers.toCStringArray(memScope)
            }
            if (enabledExtensions.isNotEmpty()) {
                enabledExtensionCount = enabledExtensions.size.toUInt()
                ppEnabledExtensionNames = enabledExtensions.toCStringArray(memScope)
            }
        }
        val instanceVar = alloc<VkInstanceVar>()
        vkCreateInstance!!(instanceCreateInfo.ptr, null, instanceVar.ptr)
            .checkResult("Failed to create a Vulkan instance")
        return Instance(instanceVar.value!!, enabledExtensions.toSet())
    }

    /**
     * List global extension properties, or the extensions provided by a layer.
     *
     * @param layerName Name of the layer to retrieve extensions from, or `null` to retrieve the extensions provided
     * by the Vulkan implementation and by implicitly enabled layers. A named layer must be available on the system.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateInstanceExtensionProperties.html">vkEnumerateInstanceExtensionProperties Manual Page</a>
     */
    fun enumerateInstanceExtensionProperties(layerName: String? = null): List<ExtensionProperties> = memScoped {
        val layerNamePtr = layerName?.cstr?.ptr
        val countVar = alloc<UIntVar>()
        var extensionProperties: CArrayPointer<VkExtensionProperties>
        var result: VkResult
        do {
            vkEnumerateInstanceExtensionProperties!!(layerNamePtr, countVar.ptr, null)
                .checkResult("Failed to enumerate instance extensions")

            val count = countVar.value.toInt()
            if (count == 0) return emptyList()

            extensionProperties = allocArray<VkExtensionProperties>(count)
            result = vkEnumerateInstanceExtensionProperties!!(layerNamePtr, countVar.ptr, extensionProperties)
        } while (result == VK_INCOMPLETE)
        result.checkResult("Failed to enumerate instance extensions")

        List(countVar.value.toInt()) { index ->
            val properties = extensionProperties[index]
            ExtensionProperties(properties.extensionName.toKString(), properties.specVersion)
        }
    }

    /**
     * List global layer properties.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumerateInstanceLayerProperties.html">vkEnumerateInstanceLayerProperties Manual Page</a>
     */
    fun enumerateInstanceLayerProperties(): List<LayerProperties> = memScoped {
        val countVar = alloc<UIntVar>()
        var layerProperties: CArrayPointer<VkLayerProperties>
        var result: VkResult
        do {
            vkEnumerateInstanceLayerProperties!!(countVar.ptr, null)
                .checkResult("Failed to enumerate instance layers")

            val count = countVar.value.toInt()
            if (count == 0) return emptyList()

            layerProperties = allocArray<VkLayerProperties>(count)
            result = vkEnumerateInstanceLayerProperties!!(countVar.ptr, layerProperties)
        } while (result == VK_INCOMPLETE)
        result.checkResult("Failed to enumerate instance layers")

        List(countVar.value.toInt()) { index ->
            val properties = layerProperties[index]
            LayerProperties(
                properties.layerName.toKString(),
                properties.specVersion,
                properties.implementationVersion,
                properties.description.toKString(),
            )
        }
    }

    context(allocator: AutofreeScope)
    private inline fun ApplicationInfo.toVkApplicationInfo(): VkApplicationInfo = allocator.alloc {
        sType = VK_STRUCTURE_TYPE_APPLICATION_INFO
        apiVersion = this@toVkApplicationInfo.apiVersion
        pApplicationName = this@toVkApplicationInfo.applicationName?.cstr?.getPointer(allocator)
        applicationVersion = this@toVkApplicationInfo.applicationVersion
        pEngineName = this@toVkApplicationInfo.engineName?.cstr?.getPointer(allocator)
        engineVersion = this@toVkApplicationInfo.engineVersion
    }
}
