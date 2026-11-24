package io.technoirlab.vulkan

import io.technoirlab.volk.VK_INCOMPLETE
import io.technoirlab.volk.VK_OBJECT_TYPE_INSTANCE
import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPhysicalDeviceVar
import io.technoirlab.volk.VkResult
import io.technoirlab.volk.vkDestroyInstance
import io.technoirlab.volk.vkEnumeratePhysicalDevices
import io.technoirlab.volk.volkLoadInstanceOnly
import io.technoirlab.vulkan.device.PhysicalDevice
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Wrapper for [VkInstance].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkInstance.html">VkInstance Manual Page</a>
 */
class Instance internal constructor(
    override val handle: VkInstance,
    val enabledExtensions: Set<String>,
) : VulkanObject,
    AutoCloseable {
    init {
        volkLoadInstanceOnly(handle)
    }

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_INSTANCE

    /**
     * List the physical devices accessible to a Vulkan instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumeratePhysicalDevices.html">vkEnumeratePhysicalDevices Manual Page</a>
     */
    fun enumeratePhysicalDevices(): List<PhysicalDevice> = memScoped {
        val countVar = alloc<UIntVar>()
        var physicalDevices: CArrayPointer<VkPhysicalDeviceVar>
        var result: VkResult
        do {
            vkEnumeratePhysicalDevices!!(handle, countVar.ptr, null)
                .checkResult("Failed to enumerate physical devices")

            val count = countVar.value.toInt()
            if (count == 0) return emptyList()

            physicalDevices = allocArray<VkPhysicalDeviceVar>(count)
            result = vkEnumeratePhysicalDevices!!(handle, countVar.ptr, physicalDevices)
        } while (result == VK_INCOMPLETE)
        result.checkResult("Failed to enumerate physical devices")

        List(countVar.value.toInt()) { index -> PhysicalDevice(physicalDevices[index]!!) }
    }

    /**
     * Destroy the instance of Vulkan.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyInstance.html">vkDestroyInstance Manual Page</a>
     */
    override fun close() {
        vkDestroyInstance!!(handle, null)
    }
}
