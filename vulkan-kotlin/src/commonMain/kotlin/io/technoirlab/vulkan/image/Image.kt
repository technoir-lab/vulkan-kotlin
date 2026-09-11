package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_OBJECT_TYPE_IMAGE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BIND_IMAGE_MEMORY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_IMAGE_TO_IMAGE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_IMAGE_TO_MEMORY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_MEMORY_TO_IMAGE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_HOST_IMAGE_LAYOUT_TRANSITION_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_MEMORY_REQUIREMENTS_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
import io.technoirlab.volk.VkBindImageMemoryInfo
import io.technoirlab.volk.VkCopyImageToImageInfo
import io.technoirlab.volk.VkCopyImageToMemoryInfo
import io.technoirlab.volk.VkCopyMemoryToImageInfo
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkHostImageCopyFlags
import io.technoirlab.volk.VkHostImageLayoutTransitionInfo
import io.technoirlab.volk.VkImage
import io.technoirlab.volk.VkImageCopy2
import io.technoirlab.volk.VkImageLayout
import io.technoirlab.volk.VkImageMemoryRequirementsInfo2
import io.technoirlab.volk.VkImageToMemoryCopy
import io.technoirlab.volk.VkMemoryRequirements2
import io.technoirlab.volk.VkMemoryToImageCopy
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkBindImageMemory2
import io.technoirlab.volk.vkCopyImageToImage
import io.technoirlab.volk.vkCopyImageToMemory
import io.technoirlab.volk.vkCopyMemoryToImage
import io.technoirlab.volk.vkDestroyImage
import io.technoirlab.volk.vkGetImageMemoryRequirements2
import io.technoirlab.volk.vkTransitionImageLayout
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.memory.DeviceMemory
import io.technoirlab.vulkan.memory.MemoryRequirements
import io.technoirlab.vulkan.memory.toMemoryRequirements
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlin.assert

/**
 * Wrapper for [VkImage].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkImage.html">VkImage Manual Page</a>
 */
class Image internal constructor(
    private val device: VkDevice,
    override val handle: VkImage,
    val destroyable: Boolean = true,
) : VulkanObject,
    AutoCloseable {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_IMAGE

    /**
     * Bind device memory to the image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkBindImageMemory2.html">vkBindImageMemory2 Manual Page</a>
     */
    fun bindMemory(memory: DeviceMemory, offset: ULong = 0uL): Unit = memScoped {
        assert(offset < memory.size) { "offset must be less than ${memory.size}" }

        val bindImageMemoryInfo = alloc<VkBindImageMemoryInfo> {
            sType = VK_STRUCTURE_TYPE_BIND_IMAGE_MEMORY_INFO
            image = handle
            memoryOffset = offset
            this.memory = memory.handle
        }
        vkBindImageMemory2!!(device, 1u, bindImageMemoryInfo.ptr)
            .checkResult("Failed to bind image memory")
    }

    /**
     * Destroy the image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyImage.html">vkDestroyImage Manual Page</a>
     */
    override fun close() {
        if (destroyable) {
            vkDestroyImage!!(device, handle, null)
        }
    }

    /**
     * Copy data from host memory to this image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCopyMemoryToImage.html">vkCopyMemoryToImage Manual Page</a>
     */
    fun copyFromMemory(destinationLayout: VkImageLayout, regions: List<HostImageCopyRegion>, flags: VkHostImageCopyFlags = 0u): Unit =
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
     * Copy this image to another image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCopyImageToImage.html">vkCopyImageToImage Manual Page</a>
     */
    fun copyToImage(
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
     * Copy this image to host memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCopyImageToMemory.html">vkCopyImageToMemory Manual Page</a>
     */
    fun copyToMemory(sourceLayout: VkImageLayout, regions: List<HostImageCopyRegion>, flags: VkHostImageCopyFlags = 0u): Unit = memScoped {
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
     * Determine memory requirements for the image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetImageMemoryRequirements2.html">vkGetImageMemoryRequirements2 Manual Page</a>
     */
    fun getMemoryRequirements(): MemoryRequirements = memScoped {
        val memoryRequirementsInfo = alloc<VkImageMemoryRequirementsInfo2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_MEMORY_REQUIREMENTS_INFO_2
            image = handle
        }
        val memoryRequirements = alloc<VkMemoryRequirements2> {
            sType = VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
        }
        vkGetImageMemoryRequirements2!!(device, memoryRequirementsInfo.ptr, memoryRequirements.ptr)
        return memoryRequirements.memoryRequirements.toMemoryRequirements()
    }

    /**
     * Transition the layout of this image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkTransitionImageLayout.html">vkTransitionImageLayout Manual Page</a>
     */
    fun transitionLayout(oldLayout: VkImageLayout, newLayout: VkImageLayout, subresourceRange: ImageSubresourceRange): Unit = memScoped {
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
}
