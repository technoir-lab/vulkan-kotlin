package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_BLIT_2
import io.technoirlab.volk.VkImageBlit2
import io.technoirlab.vulkan.Offset3D
import io.technoirlab.vulkan.from
import kotlinx.cinterop.get

/**
 * Source and destination bounds for an image blit.
 *
 * Bounds may be reversed along an axis to flip the image.
 *
 * @property sourceSubresource The source image aspects, mip level, and array layers.
 * @property destinationSubresource The destination image aspects, mip level, and array layers.
 * @property sourceEnd The second source bound, in texels.
 * @property destinationEnd The second destination bound, in texels.
 * @property sourceStart The first source bound, in texels.
 * @property destinationStart The first destination bound, in texels.
 */
data class ImageBlitRegion(
    val sourceSubresource: ImageSubresourceLayers,
    val destinationSubresource: ImageSubresourceLayers,
    val sourceEnd: Offset3D,
    val destinationEnd: Offset3D,
    val sourceStart: Offset3D = Offset3D(0, 0, 0),
    val destinationStart: Offset3D = Offset3D(0, 0, 0),
)

internal inline fun VkImageBlit2.from(region: ImageBlitRegion) {
    sType = VK_STRUCTURE_TYPE_IMAGE_BLIT_2
    pNext = null
    srcSubresource.from(region.sourceSubresource)
    srcOffsets[0].from(region.sourceStart)
    srcOffsets[1].from(region.sourceEnd)
    dstSubresource.from(region.destinationSubresource)
    dstOffsets[0].from(region.destinationStart)
    dstOffsets[1].from(region.destinationEnd)
}
