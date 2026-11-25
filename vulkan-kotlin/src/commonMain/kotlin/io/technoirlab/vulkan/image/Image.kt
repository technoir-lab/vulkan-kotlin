package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_OBJECT_TYPE_IMAGE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BIND_IMAGE_MEMORY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_MEMORY_REQUIREMENTS_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_DEDICATED_REQUIREMENTS
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
import io.technoirlab.volk.VkBindImageMemoryInfo
import io.technoirlab.volk.VkImage
import io.technoirlab.volk.VkImageMemoryRequirementsInfo2
import io.technoirlab.volk.VkMemoryDedicatedRequirements
import io.technoirlab.volk.VkMemoryRequirements2
import io.technoirlab.volk.VkObjectType
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.device.Device
import io.technoirlab.vulkan.memory.DeviceMemory
import io.technoirlab.vulkan.memory.MemoryRequirements
import io.technoirlab.vulkan.memory.toMemoryRequirements
import kotlinx.cinterop.alloc
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
    internal val device: Device,
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
        device.dispatch.vkBindImageMemory2!!(device.handle, 1u, bindImageMemoryInfo.ptr)
            .checkResult("Failed to bind image memory")
    }

    /**
     * Destroy the image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyImage.html">vkDestroyImage Manual Page</a>
     */
    override fun close() {
        if (destroyable) {
            device.dispatch.vkDestroyImage!!(device.handle, handle, null)
        }
    }

    /**
     * Determine memory requirements for the image, including dedicated-allocation flags.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetImageMemoryRequirements2.html">vkGetImageMemoryRequirements2 Manual Page</a>
     */
    fun getMemoryRequirements(): MemoryRequirements = memScoped {
        val memoryRequirementsInfo = alloc<VkImageMemoryRequirementsInfo2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_MEMORY_REQUIREMENTS_INFO_2
            image = handle
        }
        val dedicatedRequirements = alloc<VkMemoryDedicatedRequirements> {
            sType = VK_STRUCTURE_TYPE_MEMORY_DEDICATED_REQUIREMENTS
        }
        val memoryRequirements = alloc<VkMemoryRequirements2> {
            sType = VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
            pNext = dedicatedRequirements.ptr
        }
        device.dispatch.vkGetImageMemoryRequirements2!!(device.handle, memoryRequirementsInfo.ptr, memoryRequirements.ptr)
        return memoryRequirements.memoryRequirements.toMemoryRequirements(dedicatedRequirements)
    }
}
