package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_COPY_2
import io.technoirlab.volk.VkImageCopy2
import io.technoirlab.vulkan.Extent3D
import io.technoirlab.vulkan.Offset3D
import io.technoirlab.vulkan.from

/**
 * A region copied between two images.
 *
 * @property sourceSubresource The source image aspects, mip level, and array layers.
 * @property destinationSubresource The destination image aspects, mip level, and array layers.
 * @property extent The dimensions of the region in texels.
 * @property sourceOffset The offset in the source image, in texels.
 * @property destinationOffset The offset in the destination image, in texels.
 */
data class ImageCopyRegion(
    val sourceSubresource: ImageSubresourceLayers,
    val destinationSubresource: ImageSubresourceLayers,
    val extent: Extent3D,
    val sourceOffset: Offset3D = Offset3D(0, 0, 0),
    val destinationOffset: Offset3D = Offset3D(0, 0, 0),
)

internal inline fun VkImageCopy2.from(region: ImageCopyRegion) {
    sType = VK_STRUCTURE_TYPE_IMAGE_COPY_2
    pNext = null
    srcSubresource.from(region.sourceSubresource)
    srcOffset.from(region.sourceOffset)
    dstSubresource.from(region.destinationSubresource)
    dstOffset.from(region.destinationOffset)
    extent.from(region.extent)
}
