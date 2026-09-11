package io.technoirlab.vulkan.command

import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_COPY_2
import io.technoirlab.volk.VkBufferCopy2

/**
 * A region copied between two buffers.
 *
 * @property size The number of bytes to copy.
 * @property sourceOffset The offset in the source buffer, in bytes.
 * @property destinationOffset The offset in the destination buffer, in bytes.
 */
data class BufferCopyRegion(
    val size: ULong,
    val sourceOffset: ULong = 0uL,
    val destinationOffset: ULong = 0uL,
)

internal inline fun VkBufferCopy2.from(region: BufferCopyRegion) {
    sType = VK_STRUCTURE_TYPE_BUFFER_COPY_2
    pNext = null
    srcOffset = region.sourceOffset
    dstOffset = region.destinationOffset
    size = region.size
}
