package io.technoirlab.vulkan.debug

import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_TAG_INFO_EXT
import io.technoirlab.volk.VkDebugUtilsLabelEXT
import io.technoirlab.volk.VkDebugUtilsObjectNameInfoEXT
import io.technoirlab.volk.VkDebugUtilsObjectTagInfoEXT
import io.technoirlab.volk.vkCmdBeginDebugUtilsLabelEXT
import io.technoirlab.volk.vkCmdEndDebugUtilsLabelEXT
import io.technoirlab.volk.vkCmdInsertDebugUtilsLabelEXT
import io.technoirlab.volk.vkQueueBeginDebugUtilsLabelEXT
import io.technoirlab.volk.vkQueueEndDebugUtilsLabelEXT
import io.technoirlab.volk.vkQueueInsertDebugUtilsLabelEXT
import io.technoirlab.volk.vkSetDebugUtilsObjectNameEXT
import io.technoirlab.volk.vkSetDebugUtilsObjectTagEXT
import io.technoirlab.vulkan.Device
import io.technoirlab.vulkan.Queue
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.command.CommandBuffer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toLong

/**
 * Begin a debug label region in the command buffer.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBeginDebugUtilsLabelEXT.html">vkCmdBeginDebugUtilsLabelEXT Manual Page</a>
 */
fun CommandBuffer.beginDebugLabel(label: String): Unit = memScoped {
    val labelInfo = alloc<VkDebugUtilsLabelEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
        pLabelName = label.cstr.ptr
    }
    vkCmdBeginDebugUtilsLabelEXT!!(handle, labelInfo.ptr)
}

/**
 * End a debug label region in the command buffer.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdEndDebugUtilsLabelEXT.html">vkCmdEndDebugUtilsLabelEXT Manual Page</a>
 */
fun CommandBuffer.endDebugLabel() {
    vkCmdEndDebugUtilsLabelEXT!!(handle)
}

/**
 * Insert a debug label in the command buffer.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdInsertDebugUtilsLabelEXT.html">vkCmdInsertDebugUtilsLabelEXT Manual Page</a>
 */
fun CommandBuffer.insertDebugLabel(label: String): Unit = memScoped {
    val labelInfo = alloc<VkDebugUtilsLabelEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
        pLabelName = label.cstr.ptr
    }
    vkCmdInsertDebugUtilsLabelEXT!!(handle, labelInfo.ptr)
}

/**
 * Set the name of a Vulkan object for debugging purposes.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkSetDebugUtilsObjectNameEXT.html">vkSetDebugUtilsObjectNameEXT Manual Page</a>
 */
fun Device.setObjectName(obj: VulkanObject, name: String): Unit = memScoped {
    val objectNameInfo = alloc<VkDebugUtilsObjectNameInfoEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT
        objectHandle = obj.handle.toLong().toULong()
        objectType = obj.type
        pObjectName = name.cstr.ptr
    }
    vkSetDebugUtilsObjectNameEXT!!(handle, objectNameInfo.ptr).checkResult("Failed to set object name")
}

/**
 * Attach arbitrary data to a Vulkan object for debugging purposes.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkSetDebugUtilsObjectTagEXT.html">vkSetDebugUtilsObjectTagEXT Manual Page</a>
 */
fun Device.setObjectTag(obj: VulkanObject, tagInfo: VkDebugUtilsObjectTagInfoEXT.() -> Unit): Unit = memScoped {
    val objectTagInfo = alloc<VkDebugUtilsObjectTagInfoEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_TAG_INFO_EXT
        objectHandle = obj.handle.toLong().toULong()
        objectType = obj.type
        tagInfo()
    }
    vkSetDebugUtilsObjectTagEXT!!(handle, objectTagInfo.ptr).checkResult("Failed to set object tag")
}

/**
 * Open a debug label region on the queue.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkQueueBeginDebugUtilsLabelEXT.html">vkQueueBeginDebugUtilsLabelEXT Manual Page</a>
 */
fun Queue.beginDebugLabel(label: String): Unit = memScoped {
    val labelInfo = alloc<VkDebugUtilsLabelEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
        pLabelName = label.cstr.ptr
    }
    vkQueueBeginDebugUtilsLabelEXT!!(handle, labelInfo.ptr)
}

/**
 * End the current debug label region on the queue.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkQueueEndDebugUtilsLabelEXT.html">vkQueueEndDebugUtilsLabelEXT Manual Page</a>
 */
fun Queue.endDebugLabel() {
    vkQueueEndDebugUtilsLabelEXT!!(handle)
}

/**
 * Insert a debug label into the queue.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkQueueInsertDebugUtilsLabelEXT.html">vkQueueInsertDebugUtilsLabelEXT Manual Page</a>
 */
fun Queue.insertDebugLabel(label: String): Unit = memScoped {
    val labelInfo = alloc<VkDebugUtilsLabelEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
        pLabelName = label.cstr.ptr
    }
    vkQueueInsertDebugUtilsLabelEXT!!(handle, labelInfo.ptr)
}
