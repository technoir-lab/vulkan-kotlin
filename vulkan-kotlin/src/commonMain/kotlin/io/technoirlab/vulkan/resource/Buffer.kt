package io.technoirlab.vulkan.resource

import io.technoirlab.volk.VK_OBJECT_TYPE_BUFFER
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BIND_BUFFER_MEMORY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_DEVICE_ADDRESS_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_MEMORY_REQUIREMENTS_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
import io.technoirlab.volk.VkBindBufferMemoryInfo
import io.technoirlab.volk.VkBuffer
import io.technoirlab.volk.VkBufferDeviceAddressInfo
import io.technoirlab.volk.VkBufferMemoryRequirementsInfo2
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkMemoryRequirements
import io.technoirlab.volk.VkMemoryRequirements2
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkBindBufferMemory2
import io.technoirlab.volk.vkDestroyBuffer
import io.technoirlab.volk.vkGetBufferDeviceAddress
import io.technoirlab.volk.vkGetBufferMemoryRequirements2
import io.technoirlab.volk.vkGetBufferOpaqueCaptureAddress
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr

/**
 * Wrapper for [VkBuffer].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkBuffer.html">VkBuffer Manual Page</a>
 */
class Buffer internal constructor(
    private val device: VkDevice,
    override val handle: VkBuffer,
    val size: ULong,
) : VulkanObject {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_BUFFER

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
     * Retrieve the device address of the start of the buffer.
     *
     * The `bufferDeviceAddress` feature must be enabled. The buffer must be sparse or bound
     * completely and contiguously to one memory allocation.
     * If the logical device represents more than one physical device, the
     * `bufferDeviceAddressMultiDevice` feature must also be enabled.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetBufferDeviceAddress.html">vkGetBufferDeviceAddress Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getDeviceAddress(): ULong {
        val addressInfo = allocator.alloc<VkBufferDeviceAddressInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_DEVICE_ADDRESS_INFO
            buffer = handle
        }
        return vkGetBufferDeviceAddress!!(device, addressInfo.ptr)
    }

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
     * Retrieve the opaque capture address of the buffer for trace capture and replay.
     *
     * The `bufferDeviceAddress` and `bufferDeviceAddressCaptureReplay` features must be enabled.
     * The buffer must have been created with
     * `VK_BUFFER_CREATE_DEVICE_ADDRESS_CAPTURE_REPLAY_BIT`.
     * If the logical device represents more than one physical device, the
     * `bufferDeviceAddressMultiDevice` feature must also be enabled.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetBufferOpaqueCaptureAddress.html">vkGetBufferOpaqueCaptureAddress Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getOpaqueCaptureAddress(): ULong {
        val addressInfo = allocator.alloc<VkBufferDeviceAddressInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_DEVICE_ADDRESS_INFO
            buffer = handle
        }
        return vkGetBufferOpaqueCaptureAddress!!(device, addressInfo.ptr)
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
