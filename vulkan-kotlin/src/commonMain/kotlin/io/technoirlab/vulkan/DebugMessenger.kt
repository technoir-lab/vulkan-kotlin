package io.technoirlab.vulkan

import io.technoirlab.volk.VK_FALSE
import io.technoirlab.volk.VK_OBJECT_TYPE_DEBUG_UTILS_MESSENGER_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT
import io.technoirlab.volk.VkDebugUtilsMessageSeverityFlagBitsEXT
import io.technoirlab.volk.VkDebugUtilsMessageTypeFlagBitsEXT
import io.technoirlab.volk.VkDebugUtilsMessageTypeFlagsEXT
import io.technoirlab.volk.VkDebugUtilsMessengerCallbackDataEXT
import io.technoirlab.volk.VkDebugUtilsMessengerEXT
import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkDestroyDebugUtilsMessengerEXT
import io.technoirlab.volk.vkSubmitDebugUtilsMessageEXT
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.alloc
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.invoke
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

/**
 * Wrapper for [VkDebugUtilsMessengerEXT].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDebugUtilsMessengerEXT.html">VkDebugUtilsMessengerEXT Manual Page</a>
 */
class DebugMessenger internal constructor(
    private val instance: VkInstance,
    override val handle: VkDebugUtilsMessengerEXT,
    private val callbackRef: StableRef<Callback>
) : Object<VkDebugUtilsMessengerEXT> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_DEBUG_UTILS_MESSENGER_EXT

    /**
     * Submit a message to the debug stream.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkSubmitDebugUtilsMessageEXT.html">vkSubmitDebugUtilsMessageEXT Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun submitMessage(
        messageSeverity: VkDebugUtilsMessageSeverityFlagBitsEXT,
        messageTypes: VkDebugUtilsMessageTypeFlagsEXT,
        callbackData: VkDebugUtilsMessengerCallbackDataEXT.() -> Unit
    ) {
        val callbackDataStruct = allocator.alloc<VkDebugUtilsMessengerCallbackDataEXT> {
            sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CALLBACK_DATA_EXT
            callbackData()
        }
        vkSubmitDebugUtilsMessageEXT!!(instance, messageSeverity, messageTypes, callbackDataStruct.ptr)
    }

    /**
     * Destroy the debug messenger.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDebugUtilsMessengerEXT.html">vkDestroyDebugUtilsMessengerEXT Manual Page</a>
     */
    override fun close() {
        vkDestroyDebugUtilsMessengerEXT!!(instance, handle, null)
        callbackRef.dispose()
    }

    /**
     * Represents a user-defined callback for the debug messenger.
     */
    fun interface Callback {
        /**
         * Invoked when an event occurs.
         *
         * @param messageSeverity the severity of the event that triggered this callback.
         * @param messageTypes the bitmask specifying which type of event(s) triggered this callback.
         * @param callbackData the callback related data.
         */
        fun onEvent(
            messageSeverity: VkDebugUtilsMessageSeverityFlagBitsEXT,
            messageTypes: VkDebugUtilsMessageTypeFlagBitsEXT,
            callbackData: VkDebugUtilsMessengerCallbackDataEXT
        )
    }
}

internal fun debugMessengerCallback(
    messageSeverity: VkDebugUtilsMessageSeverityFlagBitsEXT,
    messageType: VkDebugUtilsMessageTypeFlagsEXT,
    callbackData: CPointer<VkDebugUtilsMessengerCallbackDataEXT>?,
    userData: COpaquePointer?
): UInt {
    val callbackData = callbackData?.pointed ?: return VK_FALSE
    val callback = userData?.asStableRef<DebugMessenger.Callback>()?.get()
    callback?.onEvent(messageSeverity, messageType, callbackData)
    return VK_FALSE
}
