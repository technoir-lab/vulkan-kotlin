package io.technoirlab.vulkan.memory

import io.technoirlab.volk.VK_OBJECT_TYPE_DEVICE_MEMORY
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_MEMORY_OPAQUE_CAPTURE_ADDRESS_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE
import io.technoirlab.volk.VK_WHOLE_SIZE
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkDeviceMemory
import io.technoirlab.volk.VkDeviceMemoryOpaqueCaptureAddressInfo
import io.technoirlab.volk.VkMappedMemoryRange
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkFlushMappedMemoryRanges
import io.technoirlab.volk.vkFreeMemory
import io.technoirlab.volk.vkGetDeviceMemoryOpaqueCaptureAddress
import io.technoirlab.volk.vkInvalidateMappedMemoryRanges
import io.technoirlab.volk.vkMapMemory
import io.technoirlab.volk.vkUnmapMemory
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.plus
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import kotlinx.io.EOFException
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
    /**
     * Size of the allocation in bytes.
     */
    val size: ULong,
) : VulkanObject,
    AutoCloseable {

    private var mappedRange: MappedRange? = null

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_DEVICE_MEMORY

    /**
     * Free the device memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkFreeMemory.html">vkFreeMemory Manual Page</a>
     */
    override fun close() {
        vkFreeMemory!!(device, handle, null)
    }

    /**
     * Copy data from a source to the device memory.
     *
     * The copied range is flushed before the memory is unmapped, making this method suitable for non-coherent
     * host-visible memory without requiring the caller to align [offset] or [expectedSize] to `nonCoherentAtomSize`.
     * The whole memory allocation is temporarily mapped and flushed to support unaligned copies. The allocation
     * must be host-visible and not already mapped. The caller must synchronize device accesses to the allocation
     * and externally synchronize host mapping operations. Flushing does not wait for device execution.
     */
    fun copyData(source: Source, expectedSize: ULong, offset: ULong = 0uL) {
        assert(expectedSize > 0uL) { "Size must be greater than 0" }
        validateRange(offset, expectedSize)
        assert(offset <= Long.MAX_VALUE.toULong() && expectedSize <= Long.MAX_VALUE.toULong() - offset) {
            "Copy range $offset + $expectedSize cannot be represented as a native pointer offset"
        }

        val mappedPtr = map(size).reinterpret<ByteVar>()
        try {
            val buffer = ByteArray(READ_BUFFER_SIZE)
            var totalRead = 0uL
            while (totalRead < expectedSize) {
                val remaining = expectedSize - totalRead
                val toRead = min(READ_BUFFER_SIZE.toULong(), remaining)
                val read = source.readAtMostTo(buffer, 0, toRead.convert())
                if (read <= 0) break
                buffer.usePinned { pinned ->
                    val destPtr = mappedPtr + offset.toLong() + totalRead.toLong()
                    memcpy(destPtr, pinned.addressOf(0), read.convert())
                }
                totalRead += read.toULong()
            }
            if (totalRead != expectedSize) {
                throw EOFException("Not enough data in source: expected $expectedSize bytes, but read $totalRead bytes")
            }
            flush()
        } finally {
            unmap()
        }
    }

    /**
     * Flush host writes in a mapped range so they become available to the device.
     *
     * The memory must be mapped, and the requested range must be contained in the current mapping. With no arguments,
     * the whole current mapping is flushed. The caller must ensure [offset] is aligned to `nonCoherentAtomSize`. An
     * explicit [size] must be a multiple of `nonCoherentAtomSize` unless the range ends at the end of this memory
     * allocation. [VK_WHOLE_SIZE] denotes the range from [offset] to the end of the current mapping; that end
     * must be atom-aligned or equal the allocation end. Offsets are relative to the allocation, and an omitted
     * [offset] starts at the mapping offset. The default range must also satisfy these alignment requirements.
     * The caller must synchronize host writes and subsequent device accesses; flushing does not wait for the device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkFlushMappedMemoryRanges.html">vkFlushMappedMemoryRanges Manual Page</a>
     */
    fun flush(offset: ULong = DEFAULT_MAPPING_OFFSET, size: ULong = VK_WHOLE_SIZE): Unit = memScoped {
        val range = resolveMappedRange(offset, size)

        val memoryRange = alloc<VkMappedMemoryRange> {
            sType = VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE
            memory = handle
            this.offset = range.offset
            this.size = range.size
        }
        vkFlushMappedMemoryRanges!!(device, 1u, memoryRange.ptr)
            .checkResult("Failed to flush mapped memory")
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
    fun getOpaqueCaptureAddress(): ULong = memScoped {
        val addressInfo = alloc<VkDeviceMemoryOpaqueCaptureAddressInfo> {
            sType = VK_STRUCTURE_TYPE_DEVICE_MEMORY_OPAQUE_CAPTURE_ADDRESS_INFO
            memory = handle
        }
        return vkGetDeviceMemoryOpaqueCaptureAddress!!(device, addressInfo.ptr)
    }

    /**
     * Invalidate a mapped range so device writes become visible to the host.
     *
     * The memory must be mapped, the requested range must be contained in the current mapping, and the caller must
     * first synchronize device writes with the host. With no arguments, the whole current mapping is invalidated.
     * [offset] must be aligned to `nonCoherentAtomSize`. An explicit [size] must be a multiple of
     * `nonCoherentAtomSize` unless the range ends at the end of this memory allocation. [VK_WHOLE_SIZE] denotes the
     * range from [offset] to the end of the current mapping; that end must be atom-aligned or equal the allocation
     * end. Offsets are relative to the allocation, and an omitted [offset] starts at the mapping offset. The default
     * range must also satisfy these alignment requirements. Host writes must be flushed before invalidating their
     * range; invalidation does not wait for device execution.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkInvalidateMappedMemoryRanges.html">vkInvalidateMappedMemoryRanges Manual Page</a>
     */
    fun invalidate(offset: ULong = DEFAULT_MAPPING_OFFSET, size: ULong = VK_WHOLE_SIZE): Unit = memScoped {
        val range = resolveMappedRange(offset, size)

        val memoryRange = alloc<VkMappedMemoryRange> {
            sType = VK_STRUCTURE_TYPE_MAPPED_MEMORY_RANGE
            memory = handle
            this.offset = range.offset
            this.size = range.size
        }
        vkInvalidateMappedMemoryRanges!!(device, 1u, memoryRange.ptr)
            .checkResult("Failed to invalidate mapped memory")
    }

    /**
     * Map the memory into application address space.
     *
     * The requested range must be contained in this allocation. The memory must not already be mapped; after a
     * successful call, [unmap] must be called before mapping it again.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkMapMemory.html">vkMapMemory Manual Page</a>
     */
    fun map(size: ULong, offset: ULong = 0uL): CPointer<out CPointed> = memScoped {
        assert(size > 0uL) { "Size must be greater than 0" }
        assert(mappedRange == null) { "Memory is already mapped" }
        val mappedSize = if (size == VK_WHOLE_SIZE) this@DeviceMemory.size - offset else size
        validateRange(offset, mappedSize)

        val mappedPtr = alloc<CPointerVar<out CPointed>>()
        vkMapMemory!!(device, handle, offset, size, 0u, mappedPtr.ptr)
            .checkResult("Failed to map memory")
        mappedRange = MappedRange(offset, mappedSize)
        return mappedPtr.value!!
    }

    /**
     * Unmap the previously mapped memory.
     *
     * The memory must be mapped.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkUnmapMemory.html">vkUnmapMemory Manual Page</a>
     */
    fun unmap() {
        assert(mappedRange != null) { "Memory is not mapped" }
        try {
            vkUnmapMemory!!(device, handle)
        } finally {
            mappedRange = null
        }
    }

    private fun resolveMappedRange(offset: ULong, size: ULong): MappedRange {
        assert(mappedRange != null) { "Memory is not mapped" }
        val mapping = mappedRange!!
        val resolvedOffset = if (offset == DEFAULT_MAPPING_OFFSET) mapping.offset else offset
        assert(resolvedOffset >= mapping.offset && resolvedOffset - mapping.offset < mapping.size) {
            "Offset $resolvedOffset must be within the current mapping"
        }
        val mappedRemaining = mapping.size - (resolvedOffset - mapping.offset)
        assert(size == VK_WHOLE_SIZE || size <= mappedRemaining) {
            "Range $resolvedOffset + $size exceeds the current mapping"
        }
        return MappedRange(resolvedOffset, size)
    }

    private fun validateRange(offset: ULong, size: ULong) {
        assert(offset < this.size && size <= this.size - offset) {
            "Offset $offset + $size exceeds total memory size ${this.size}"
        }
    }

    private data class MappedRange(
        val offset: ULong,
        val size: ULong,
    )

    /**
     * Constants used by device memory operations.
     */
    companion object {
        private const val DEFAULT_MAPPING_OFFSET = VK_WHOLE_SIZE
        private const val READ_BUFFER_SIZE = 64 * 1024
    }
}
