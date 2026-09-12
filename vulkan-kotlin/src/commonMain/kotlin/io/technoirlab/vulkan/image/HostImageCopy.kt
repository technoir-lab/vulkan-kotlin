package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_IMAGE_TO_IMAGE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_IMAGE_TO_MEMORY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_MEMORY_TO_IMAGE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_HOST_IMAGE_LAYOUT_TRANSITION_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_TO_MEMORY_COPY
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_TO_IMAGE_COPY
import io.technoirlab.volk.VkCopyImageToImageInfo
import io.technoirlab.volk.VkCopyImageToMemoryInfo
import io.technoirlab.volk.VkCopyMemoryToImageInfo
import io.technoirlab.volk.VkHostImageCopyFlags
import io.technoirlab.volk.VkHostImageLayoutTransitionInfo
import io.technoirlab.volk.VkImageCopy2
import io.technoirlab.volk.VkImageLayout
import io.technoirlab.volk.VkImageToMemoryCopy
import io.technoirlab.volk.VkMemoryToImageCopy
import io.technoirlab.volk.vkCopyImageToImage
import io.technoirlab.volk.vkCopyImageToMemory
import io.technoirlab.volk.vkCopyMemoryToImage
import io.technoirlab.volk.vkTransitionImageLayout
import io.technoirlab.vulkan.Extent3D
import io.technoirlab.vulkan.Offset3D
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.from
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlin.assert

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

/**
 * Copy data from the host memory to this image.
 *
 * Requires the `hostImageCopy` feature to be enabled on the device. This command uses the Vulkan 1.4 core API.
 * The image must have been created with `VK_IMAGE_USAGE_HOST_TRANSFER_BIT` for the accessed aspects.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCopyMemoryToImage.html">vkCopyMemoryToImage Manual Page</a>
 */
fun Image.copyFromMemory(destinationLayout: VkImageLayout, regions: List<HostImageCopyRegion>, flags: VkHostImageCopyFlags = 0u): Unit =
    memScoped {
        assert(regions.isNotEmpty()) { "regions must not be empty" }
        val regionCount = regions.size.toUInt()
        val regions = allocArray<VkMemoryToImageCopy>(regions.size) { from(regions[it]) }
        val copyInfo = alloc<VkCopyMemoryToImageInfo> {
            sType = VK_STRUCTURE_TYPE_COPY_MEMORY_TO_IMAGE_INFO
            this.flags = flags
            dstImage = handle
            dstImageLayout = destinationLayout
            this.regionCount = regionCount
            pRegions = regions
        }
        vkCopyMemoryToImage!!(device, copyInfo.ptr)
            .checkResult("Failed to copy host memory to image")
    }

/**
 * Copy this image to another image on the host.
 *
 * Requires the `hostImageCopy` feature to be enabled on the device. This command uses the Vulkan 1.4 core API.
 * Both images must have been created with `VK_IMAGE_USAGE_HOST_TRANSFER_BIT` for the copied aspects.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCopyImageToImage.html">vkCopyImageToImage Manual Page</a>
 */
fun Image.copyToImage(
    destination: Image,
    sourceLayout: VkImageLayout,
    destinationLayout: VkImageLayout,
    regions: List<ImageCopyRegion>,
    flags: VkHostImageCopyFlags = 0u,
): Unit = memScoped {
    assert(regions.isNotEmpty()) { "regions must not be empty" }
    val regionCount = regions.size.toUInt()
    val regions = allocArray<VkImageCopy2>(regions.size) { from(regions[it]) }
    val copyInfo = alloc<VkCopyImageToImageInfo> {
        sType = VK_STRUCTURE_TYPE_COPY_IMAGE_TO_IMAGE_INFO
        this.flags = flags
        srcImage = handle
        srcImageLayout = sourceLayout
        dstImage = destination.handle
        dstImageLayout = destinationLayout
        this.regionCount = regionCount
        pRegions = regions
    }
    vkCopyImageToImage!!(device, copyInfo.ptr)
        .checkResult("Failed to copy image to image")
}

/**
 * Copy this image to the host memory.
 *
 * Requires the `hostImageCopy` feature to be enabled on the device. This command uses the Vulkan 1.4 core API.
 * The image must have been created with `VK_IMAGE_USAGE_HOST_TRANSFER_BIT` for the accessed aspects.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCopyImageToMemory.html">vkCopyImageToMemory Manual Page</a>
 */
fun Image.copyToMemory(sourceLayout: VkImageLayout, regions: List<HostImageCopyRegion>, flags: VkHostImageCopyFlags = 0u): Unit =
    memScoped {
        assert(regions.isNotEmpty()) { "regions must not be empty" }
        val regionCount = regions.size.toUInt()
        val regions = allocArray<VkImageToMemoryCopy>(regions.size) { from(regions[it]) }
        val copyInfo = alloc<VkCopyImageToMemoryInfo> {
            sType = VK_STRUCTURE_TYPE_COPY_IMAGE_TO_MEMORY_INFO
            this.flags = flags
            srcImage = handle
            srcImageLayout = sourceLayout
            this.regionCount = regionCount
            pRegions = regions
        }
        vkCopyImageToMemory!!(device, copyInfo.ptr)
            .checkResult("Failed to copy image to host memory")
    }

/**
 * Transition the layout of this image on the host.
 *
 * Requires the `hostImageCopy` feature to be enabled on the device. This command uses the Vulkan 1.4 core API.
 * The image must have been created with `VK_IMAGE_USAGE_HOST_TRANSFER_BIT` for the accessed aspects.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkTransitionImageLayout.html">vkTransitionImageLayout Manual Page</a>
 */
fun Image.transitionLayout(oldLayout: VkImageLayout, newLayout: VkImageLayout, subresourceRange: ImageSubresourceRange): Unit = memScoped {
    val transition = alloc<VkHostImageLayoutTransitionInfo> {
        sType = VK_STRUCTURE_TYPE_HOST_IMAGE_LAYOUT_TRANSITION_INFO
        image = handle
        this.oldLayout = oldLayout
        this.newLayout = newLayout
        this.subresourceRange.from(subresourceRange)
    }
    vkTransitionImageLayout!!(device, 1u, transition.ptr)
        .checkResult("Failed to transition image layout")
}

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
