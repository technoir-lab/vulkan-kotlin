package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_TO_MEMORY_COPY
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_TO_IMAGE_COPY
import io.technoirlab.volk.VkImageToMemoryCopy
import io.technoirlab.volk.VkMemoryToImageCopy
import io.technoirlab.vulkan.Extent3D
import io.technoirlab.vulkan.Offset3D
import io.technoirlab.vulkan.from
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer

/**
 * A region copied between host memory and an image.
 *
 * This value does not own the host memory. The caller must keep it valid for the entire copy call
 * and provide enough readable or writable memory for the copy direction and packing.
 *
 * @property hostPointer The source or destination host memory address.
 * @property imageSubresource The image aspects, mip level, and array layers to access.
 * @property imageExtent The dimensions of the image region in texels.
 * @property imageOffset The offset in the image, in texels.
 * @property memoryRowLength The row length in texels, or zero for tightly packed rows.
 * @property memoryImageHeight The image height in texels, or zero for tightly packed slices.
 */
data class HostImageCopyRegion(
    val hostPointer: CPointer<out CPointed>,
    val imageSubresource: ImageSubresourceLayers,
    val imageExtent: Extent3D,
    val imageOffset: Offset3D = Offset3D(0, 0, 0),
    val memoryRowLength: UInt = 0u,
    val memoryImageHeight: UInt = 0u,
)

internal inline fun VkImageToMemoryCopy.from(region: HostImageCopyRegion) {
    sType = VK_STRUCTURE_TYPE_IMAGE_TO_MEMORY_COPY
    pNext = null
    pHostPointer = region.hostPointer
    memoryRowLength = region.memoryRowLength
    memoryImageHeight = region.memoryImageHeight
    imageSubresource.from(region.imageSubresource)
    imageOffset.from(region.imageOffset)
    imageExtent.from(region.imageExtent)
}

internal inline fun VkMemoryToImageCopy.from(region: HostImageCopyRegion) {
    sType = VK_STRUCTURE_TYPE_MEMORY_TO_IMAGE_COPY
    pNext = null
    pHostPointer = region.hostPointer
    memoryRowLength = region.memoryRowLength
    memoryImageHeight = region.memoryImageHeight
    imageSubresource.from(region.imageSubresource)
    imageOffset.from(region.imageOffset)
    imageExtent.from(region.imageExtent)
}
