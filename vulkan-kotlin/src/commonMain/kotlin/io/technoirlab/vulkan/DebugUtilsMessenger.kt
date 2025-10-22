package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_DEBUG_UTILS_MESSENGER_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT
import io.technoirlab.volk.VkDebugUtilsMessageSeverityFlagBitsEXT
import io.technoirlab.volk.VkDebugUtilsMessageTypeFlagsEXT
import io.technoirlab.volk.VkDebugUtilsMessengerCallbackDataEXT
import io.technoirlab.volk.VkDebugUtilsMessengerEXT
import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkDestroyDebugUtilsMessengerEXT
import io.technoirlab.volk.vkSubmitDebugUtilsMessageEXT
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr

/**
 * Wrapper for [VkDebugUtilsMessengerEXT].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDebugUtilsMessengerEXT.html">VkDebugUtilsMessengerEXT Manual Page</a>
 */
class DebugUtilsMessenger(
    private val instance: VkInstance,
    override val handle: VkDebugUtilsMessengerEXT
) : Object<VkDebugUtilsMessengerEXT> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_DEBUG_UTILS_MESSENGER_EXT

    /**
     * Submit a message to the debug callback.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkSubmitDebugUtilsMessageEXT.html">vkSubmitDebugUtilsMessageEXT Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun submitMessage(
        severity: VkDebugUtilsMessageSeverityFlagBitsEXT,
        types: VkDebugUtilsMessageTypeFlagsEXT,
        callbackData: VkDebugUtilsMessengerCallbackDataEXT.() -> Unit
    ) {
        val callbackDataStruct = allocator.alloc<VkDebugUtilsMessengerCallbackDataEXT> {
            sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT
            callbackData()
        }
        vkSubmitDebugUtilsMessageEXT!!(instance, severity, types, callbackDataStruct.ptr)
    }

    /**
     * Destroy the debug messenger.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDebugUtilsMessengerEXT.html">vkDestroyDebugUtilsMessengerEXT Manual Page</a>
     */
    override fun close() {
        vkDestroyDebugUtilsMessengerEXT!!(instance, handle, null)
    }
}
