package io.technoirlab.vulkan

import io.technoirlab.volk.VK_INCOMPLETE
import io.technoirlab.volk.VK_OBJECT_TYPE_INSTANCE
import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPhysicalDeviceVar
import io.technoirlab.volk.VkResult
import io.technoirlab.volk.VolkInstanceTable
import io.technoirlab.vulkan.device.PhysicalDevice
import io.technoirlab.vulkan.internal.DispatchLoader
import io.technoirlab.vulkan.internal.DispatchTable
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
    private val dispatchTable: DispatchTable<VolkInstanceTable>,
    internal val dispatchLoader: DispatchLoader,
) : VulkanObject,
    AutoCloseable {
    /**
     * The instance-level commands resolved for this instance, including those of its physical devices.
     *
     * Use it to call commands that this library does not wrap. The table belongs to the instance: closing the
     * instance clears it, and it must not be used once the instance object is unreachable.
     */
    val dispatch = dispatchTable.commands

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
            dispatch.vkEnumeratePhysicalDevices!!(handle, countVar.ptr, null)
                .checkResult("Failed to enumerate physical devices")

            val count = countVar.value.toInt()
            if (count == 0) return emptyList()

            physicalDevices = allocArray<VkPhysicalDeviceVar>(count)
            result = dispatch.vkEnumeratePhysicalDevices!!(handle, countVar.ptr, physicalDevices)
        } while (result == VK_INCOMPLETE)
        result.checkResult("Failed to enumerate physical devices")

        List(countVar.value.toInt()) { index -> PhysicalDevice(this@Instance, physicalDevices[index]!!) }
    }

    /**
     * Destroy the instance of Vulkan.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyInstance.html">vkDestroyInstance Manual Page</a>
     */
    override fun close() {
        dispatch.vkDestroyInstance!!(handle, null)
        dispatchLoader.unload(dispatchTable)
    }
}
