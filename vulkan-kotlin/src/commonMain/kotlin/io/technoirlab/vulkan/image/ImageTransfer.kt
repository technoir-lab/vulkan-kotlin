package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_RESOLVE_MODE_NONE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_IMAGE_COPY_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_BLIT_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_COPY_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_RESOLVE_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_RESOLVE_IMAGE_MODE_INFO_KHR
import io.technoirlab.volk.VkBufferImageCopy2
import io.technoirlab.volk.VkImageBlit2
import io.technoirlab.volk.VkImageCopy2
import io.technoirlab.volk.VkImageResolve2
import io.technoirlab.volk.VkResolveImageFlagsKHR
import io.technoirlab.volk.VkResolveImageModeInfoKHR
import io.technoirlab.volk.VkResolveModeFlagBits
import io.technoirlab.vulkan.Extent3D
import io.technoirlab.vulkan.Offset3D
import io.technoirlab.vulkan.from
import kotlinx.cinterop.get

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

/**
 * A region resolved from a multisampled image to a single-sampled image.
 *
 * @property sourceSubresource The source image aspects, mip level, and array layers.
 * @property destinationSubresource The destination image aspects, mip level, and array layers.
 * @property extent The dimensions of the region in texels.
 * @property sourceOffset The offset in the source image, in texels.
 * @property destinationOffset The offset in the destination image, in texels.
 */
data class ImageResolveRegion(
    val sourceSubresource: ImageSubresourceLayers,
    val destinationSubresource: ImageSubresourceLayers,
    val extent: Extent3D,
    val sourceOffset: Offset3D = Offset3D(0, 0, 0),
    val destinationOffset: Offset3D = Offset3D(0, 0, 0),
)

/**
 * Additional resolve controls provided by `VK_KHR_maintenance10`.
 *
 * The extension and its `maintenance10` feature must be enabled when using these controls.
 *
 * @property resolveMode The resolve mode for color or depth aspects, or [VK_RESOLVE_MODE_NONE] to leave them unresolved.
 * @property stencilResolveMode The resolve mode for the stencil aspect, or [VK_RESOLVE_MODE_NONE] to leave it unresolved.
 * @property flags Flags controlling the sRGB transfer function used during the resolve.
 */
data class ResolveImageModeInfo(
    val resolveMode: VkResolveModeFlagBits = VK_RESOLVE_MODE_NONE,
    val stencilResolveMode: VkResolveModeFlagBits = VK_RESOLVE_MODE_NONE,
    val flags: VkResolveImageFlagsKHR = 0u,
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

internal inline fun VkImageCopy2.from(region: ImageCopyRegion) {
    sType = VK_STRUCTURE_TYPE_IMAGE_COPY_2
    pNext = null
    srcSubresource.from(region.sourceSubresource)
    srcOffset.from(region.sourceOffset)
    dstSubresource.from(region.destinationSubresource)
    dstOffset.from(region.destinationOffset)
    extent.from(region.extent)
}

internal inline fun VkImageResolve2.from(region: ImageResolveRegion) {
    sType = VK_STRUCTURE_TYPE_IMAGE_RESOLVE_2
    pNext = null
    srcSubresource.from(region.sourceSubresource)
    srcOffset.from(region.sourceOffset)
    dstSubresource.from(region.destinationSubresource)
    dstOffset.from(region.destinationOffset)
    extent.from(region.extent)
}

internal inline fun VkResolveImageModeInfoKHR.from(info: ResolveImageModeInfo) {
    sType = VK_STRUCTURE_TYPE_RESOLVE_IMAGE_MODE_INFO_KHR
    pNext = null
    flags = info.flags
    resolveMode = info.resolveMode
    stencilResolveMode = info.stencilResolveMode
}
