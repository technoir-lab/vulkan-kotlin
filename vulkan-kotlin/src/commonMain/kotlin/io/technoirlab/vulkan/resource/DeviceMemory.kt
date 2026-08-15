package io.technoirlab.vulkan.resource

import io.technoirlab.volk.VK_OBJECT_TYPE_DEVICE_MEMORY
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_MEMORY_OPAQUE_CAPTURE_ADDRESS_INFO
import io.technoirlab.volk.VK_WHOLE_SIZE
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkDeviceMemory
import io.technoirlab.volk.VkDeviceMemoryOpaqueCaptureAddressInfo
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkFreeMemory
import io.technoirlab.volk.vkGetDeviceMemoryOpaqueCaptureAddress
import io.technoirlab.volk.vkMapMemory
import io.technoirlab.volk.vkUnmapMemory
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.invoke
import kotlinx.cinterop.plus
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import kotlinx.io.Source
import platform.posix.memcpy
import kotlin.assert
import kotlin.math.min

/**
 * Wrapper for [VkDeviceMemory].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDeviceMemory.html">VkDeviceMemory Manual Page</a>
 */
class DeviceMemory internal constructor(
    private val device: VkDevice,
    override val handle: VkDeviceMemory,
    val size: ULong,
) : VulkanObject,
    AutoCloseable {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_DEVICE_MEMORY

    /**
     * Copy data from a source to the device memory.
     */
    context(allocator: NativePlacement)
    fun copyData(source: Source, expectedSize: ULong, offset: ULong = 0uL) {
        val mappedPtr = map(expectedSize, offset).reinterpret<ByteVar>()
        try {
            val buffer = ByteArray(READ_BUFFER_SIZE)
            var totalRead = 0uL
            while (totalRead < expectedSize) {
                val remaining = expectedSize - totalRead
                val toRead = min(READ_BUFFER_SIZE.toULong(), remaining)
                val read = source.readAtMostTo(buffer, 0, toRead.convert())
                if (read <= 0) break
                buffer.usePinned { pinned ->
                    val destPtr = mappedPtr + totalRead.toLong()
                    memcpy(destPtr, pinned.addressOf(0), read.convert())
                }
                totalRead += read.toULong()
            }
            assert(totalRead == expectedSize) {
                "Not enough data in source: expected $expectedSize bytes, but read $totalRead bytes"
            }
        } finally {
            unmap()
        }
    }

    /**
     * Retrieve the opaque capture address of this allocation for trace capture and replay.
     *
     * The `bufferDeviceAddress` and `bufferDeviceAddressCaptureReplay` features must be enabled.
     * The memory must have been allocated with `VK_MEMORY_ALLOCATE_DEVICE_ADDRESS_CAPTURE_REPLAY_BIT`.
     * If the logical device represents more than one physical device, the
     * `bufferDeviceAddressMultiDevice` feature must also be enabled.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetDeviceMemoryOpaqueCaptureAddress.html">vkGetDeviceMemoryOpaqueCaptureAddress Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getOpaqueCaptureAddress(): ULong {
        val addressInfo = allocator.alloc<VkDeviceMemoryOpaqueCaptureAddressInfo> {
            sType = VK_STRUCTURE_TYPE_DEVICE_MEMORY_OPAQUE_CAPTURE_ADDRESS_INFO
            memory = handle
        }
        return vkGetDeviceMemoryOpaqueCaptureAddress!!(device, addressInfo.ptr)
    }

    /**
     * Map the memory into application address space.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkMapMemory.html">vkMapMemory Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun map(size: ULong, offset: ULong = 0uL): CPointer<out CPointed> {
        assert(offset < this.size) { "offset must be less than ${this.size}" }
        assert(size == VK_WHOLE_SIZE || size > 0uL) { "size must be greater than 0" }
        assert(size == VK_WHOLE_SIZE || size <= this.size - offset) {
            "size must be less than or equal to ${this.size} - offset"
        }

        val mappedPtr = allocator.alloc<CPointerVar<out CPointed>>()
        vkMapMemory!!(device, handle, offset, size, 0u, mappedPtr.ptr)
            .checkResult("Failed to map memory")
        return mappedPtr.value!!
    }

    /**
     * Unmap the previously mapped memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkUnmapMemory.html">vkUnmapMemory Manual Page</a>
     */
    fun unmap() {
        vkUnmapMemory!!(device, handle)
    }

    /**
     * Free the device memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkFreeMemory.html">vkFreeMemory Manual Page</a>
     */
    override fun close() {
        vkFreeMemory!!(device, handle, null)
    }

    companion object {
        private const val READ_BUFFER_SIZE = 64 * 1024
    }
}
