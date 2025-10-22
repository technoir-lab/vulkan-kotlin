package io.technoirlab.vulkan

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
import kotlinx.cinterop.AutofreeScope
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toLong

/**
 * Begin a debug label region in the command buffer.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBeginDebugUtilsLabelEXT.html">vkCmdBeginDebugUtilsLabelEXT Manual Page</a>
 */
context(allocator: AutofreeScope)
fun CommandBuffer.beginDebugLabel(label: String) {
    val labelInfo = allocator.alloc<VkDebugUtilsLabelEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
        pLabelName = label.cstr.getPointer(allocator)
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
context(allocator: AutofreeScope)
fun CommandBuffer.insertDebugLabel(label: String) {
    val labelInfo = allocator.alloc<VkDebugUtilsLabelEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
        pLabelName = label.cstr.getPointer(allocator)
    }
    vkCmdInsertDebugUtilsLabelEXT!!(handle, labelInfo.ptr)
}

/**
 * Set the name of a Vulkan object for debugging purposes.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkSetDebugUtilsObjectNameEXT.html">vkSetDebugUtilsObjectNameEXT Manual Page</a>
 */
context(allocator: AutofreeScope)
fun Device.setObjectName(obj: Object<*>, name: String) {
    val objectNameInfo = allocator.alloc<VkDebugUtilsObjectNameInfoEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT
        objectHandle = obj.handle.toLong().toULong()
        objectType = obj.type
        pObjectName = name.cstr.getPointer(allocator)
    }
    vkSetDebugUtilsObjectNameEXT!!(handle, objectNameInfo.ptr).checkResult("Failed to set object name")
}

/**
 * Attach arbitrary data to a Vulkan object for debugging purposes.
 * Requires `VK_EXT_debug_utils` extension.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkSetDebugUtilsObjectTagEXT.html">vkSetDebugUtilsObjectTagEXT Manual Page</a>
 */
context(allocator: AutofreeScope)
fun Device.setObjectTag(obj: Object<*>, tagInfo: VkDebugUtilsObjectTagInfoEXT.() -> Unit) {
    val objectTagInfo = allocator.alloc<VkDebugUtilsObjectTagInfoEXT> {
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
context(allocator: AutofreeScope)
fun Queue.beginDebugLabel(label: String) {
    val labelInfo = allocator.alloc<VkDebugUtilsLabelEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
        pLabelName = label.cstr.getPointer(allocator)
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
context(allocator: AutofreeScope)
fun Queue.insertDebugLabel(label: String) {
    val labelInfo = allocator.alloc<VkDebugUtilsLabelEXT> {
        sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_LABEL_EXT
        pLabelName = label.cstr.getPointer(allocator)
    }
    vkQueueInsertDebugUtilsLabelEXT!!(handle, labelInfo.ptr)
}
