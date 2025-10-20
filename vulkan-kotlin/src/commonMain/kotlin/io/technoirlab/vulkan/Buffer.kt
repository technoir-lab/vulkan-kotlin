package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_BIND_BUFFER_MEMORY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_MEMORY_REQUIREMENTS_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
import io.technoirlab.volk.VkBindBufferMemoryInfo
import io.technoirlab.volk.VkBuffer
import io.technoirlab.volk.VkBufferMemoryRequirementsInfo2
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkMemoryRequirements
import io.technoirlab.volk.VkMemoryRequirements2
import io.technoirlab.volk.vkBindBufferMemory2
import io.technoirlab.volk.vkDestroyBuffer
import io.technoirlab.volk.vkGetBufferMemoryRequirements2
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr

/**
 * Wrapper for [VkBuffer].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkBuffer.html">VkBuffer Manual Page</a>
 */
class Buffer(
    private val device: VkDevice,
    override val handle: VkBuffer,
    val size: ULong
) : Object<VkBuffer> {

    /**
     * Determine memory requirements for the buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetBufferMemoryRequirements2.html">vkGetBufferMemoryRequirements2 Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getMemoryRequirements(): VkMemoryRequirements {
        val memoryRequirementsInfo = allocator.alloc<VkBufferMemoryRequirementsInfo2> {
            sType = VK_STRUCTURE_TYPE_BUFFER_MEMORY_REQUIREMENTS_INFO_2
            buffer = handle
        }
        val memoryRequirements = allocator.alloc<VkMemoryRequirements2> {
            sType = VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
        }
        vkGetBufferMemoryRequirements2!!(device, memoryRequirementsInfo.ptr, memoryRequirements.ptr)
        return memoryRequirements.memoryRequirements
    }

    /**
     * Bind device memory to the buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkBindBufferMemory2.html">vkBindBufferMemory2 Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun bindMemory(memory: DeviceMemory, offset: ULong = 0uL) {
        val bindImageMemoryInfo = allocator.alloc<VkBindBufferMemoryInfo> {
            sType = VK_STRUCTURE_TYPE_BIND_BUFFER_MEMORY_INFO
            buffer = handle
            memoryOffset = offset
            this.memory = memory.handle
        }
        vkBindBufferMemory2!!(device, 1u, bindImageMemoryInfo.ptr)
            .checkResult("Failed to bind buffer memory")
    }

    /**
     * Destroy the buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyBuffer.html">vkDestroyBuffer Manual Page</a>
     */
    override fun close() {
        vkDestroyBuffer!!(device, handle, null)
    }
}
