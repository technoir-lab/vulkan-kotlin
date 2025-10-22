package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_INSTANCE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT
import io.technoirlab.volk.VkDebugUtilsMessageSeverityFlagsEXT
import io.technoirlab.volk.VkDebugUtilsMessageTypeFlagsEXT
import io.technoirlab.volk.VkDebugUtilsMessengerCreateInfoEXT
import io.technoirlab.volk.VkDebugUtilsMessengerEXTVar
import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPhysicalDeviceVar
import io.technoirlab.volk.vkCreateDebugUtilsMessengerEXT
import io.technoirlab.volk.vkDestroyInstance
import io.technoirlab.volk.vkEnumeratePhysicalDevices
import io.technoirlab.volk.volkLoadInstanceOnly
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.value

/**
 * Wrapper for [VkInstance].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkInstance.html">VkInstance Manual Page</a>
 */
class Instance(
    override val handle: VkInstance
) : Object<VkInstance> {

    init {
        volkLoadInstanceOnly(handle)
    }

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_INSTANCE

    /**
     * Create a debug messenger.
     * Requires `VK_EXT_debug_utils` extension.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDebugUtilsMessengerEXT.html">vkCreateDebugUtilsMessengerEXT Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun createDebugMessenger(
        messageSeverity: VkDebugUtilsMessageSeverityFlagsEXT,
        messageType: VkDebugUtilsMessageTypeFlagsEXT,
        callback: DebugMessenger.Callback
    ): DebugMessenger {
        val callbackRef = StableRef.create(callback)
        val debugUtilsMessengerCreateInfo = allocator.alloc<VkDebugUtilsMessengerCreateInfoEXT> {
            sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT
            pfnUserCallback = staticCFunction(::debugMessengerCallback)
            pUserData = callbackRef.asCPointer()
            this.messageSeverity = messageSeverity
            this.messageType = messageType
        }
        val messengerVar = allocator.alloc<VkDebugUtilsMessengerEXTVar>()
        vkCreateDebugUtilsMessengerEXT!!(handle, debugUtilsMessengerCreateInfo.ptr, null, messengerVar.ptr)
            .checkResult("Failed to create a debug messenger")
        return DebugMessenger(handle, messengerVar.value!!, callbackRef)
    }

    /**
     * List the physical devices accessible to a Vulkan instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEnumeratePhysicalDevices.html">vkEnumeratePhysicalDevices Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun enumeratePhysicalDevices(): List<PhysicalDevice> {
        val countVar = allocator.alloc<UIntVar>()
        vkEnumeratePhysicalDevices!!(handle, countVar.ptr, null)
            .checkResult("Failed to enumerate physical devices")

        val count = countVar.value.toLong()
        if (count == 0L) return emptyList()

        val physicalDevices = allocator.allocArray<VkPhysicalDeviceVar>(count)
        vkEnumeratePhysicalDevices!!(handle, countVar.ptr, physicalDevices)
            .checkResult("Failed to enumerate physical devices")

        return (0 until count).map { PhysicalDevice(physicalDevices[it]!!) }
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
