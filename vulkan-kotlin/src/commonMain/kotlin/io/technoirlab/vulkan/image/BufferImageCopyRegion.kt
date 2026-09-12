package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_IMAGE_COPY_2
import io.technoirlab.volk.VkBufferImageCopy2
import io.technoirlab.vulkan.Extent3D
import io.technoirlab.vulkan.Offset3D
import io.technoirlab.vulkan.from

/**
 * A region copied between a buffer and an image.
 *
 * @property imageSubresource The image aspects, mip level, and array layers to access.
 * @property imageExtent The dimensions of the image region in texels.
 * @property imageOffset The offset in the image, in texels.
 * @property bufferOffset The offset in the buffer, in bytes.
 * @property bufferRowLength The row length in texels, or zero for tightly packed rows.
 * @property bufferImageHeight The image height in texels, or zero for tightly packed slices.
 */
data class BufferImageCopyRegion(
    val imageSubresource: ImageSubresourceLayers,
    val imageExtent: Extent3D,
    val imageOffset: Offset3D = Offset3D(0, 0, 0),
    val bufferOffset: ULong = 0uL,
    val bufferRowLength: UInt = 0u,
    val bufferImageHeight: UInt = 0u,
)

internal inline fun VkBufferImageCopy2.from(region: BufferImageCopyRegion) {
    sType = VK_STRUCTURE_TYPE_BUFFER_IMAGE_COPY_2
    pNext = null
    bufferOffset = region.bufferOffset
    bufferRowLength = region.bufferRowLength
    bufferImageHeight = region.bufferImageHeight
    imageSubresource.from(region.imageSubresource)
    imageOffset.from(region.imageOffset)
    imageExtent.from(region.imageExtent)
}
