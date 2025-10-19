package io.technoirlab.vulkan

import io.technoirlab.volk.VK_ERROR_OUT_OF_DATE_KHR
import io.technoirlab.volk.VK_OBJECT_TYPE_SWAPCHAIN_KHR
import io.technoirlab.volk.VK_STRUCTURE_TYPE_ACQUIRE_NEXT_IMAGE_INFO_KHR
import io.technoirlab.volk.VK_SUBOPTIMAL_KHR
import io.technoirlab.volk.VkAcquireNextImageInfoKHR
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkImageVar
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkSwapchainKHR
import io.technoirlab.volk.vkAcquireNextImage2KHR
import io.technoirlab.volk.vkDestroySwapchainKHR
import io.technoirlab.volk.vkGetSwapchainImagesKHR
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.time.Duration

/**
 * Wrapper for [VkSwapchainKHR].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkSwapchainKHR.html">VkSwapchainKHR Manual Page</a>
 */
class Swapchain(
    private val device: VkDevice,
    override val handle: VkSwapchainKHR
) : Object<VkSwapchainKHR> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_SWAPCHAIN_KHR

    /**
     * Retrieve the index of the next available presentable image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAcquireNextImage2KHR.html">vkAcquireNextImage2KHR Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun acquireNextImage(
        semaphore: Semaphore? = null,
        fence: Fence? = null,
        timeout: Duration = Duration.INFINITE
    ): Result<UInt> {
        val acquireInfo = allocator.alloc<VkAcquireNextImageInfoKHR> {
            sType = VK_STRUCTURE_TYPE_ACQUIRE_NEXT_IMAGE_INFO_KHR
            deviceMask = 1u
            swapchain = handle
            this.semaphore = semaphore?.handle
            this.fence = fence?.handle
            this.timeout = timeout.inWholeNanosecondsULong
        }
        val imageIndexVar = allocator.alloc<UIntVar>()
        val result = vkAcquireNextImage2KHR!!(device, acquireInfo.ptr, imageIndexVar.ptr)
        if (result != VK_ERROR_OUT_OF_DATE_KHR && result != VK_SUBOPTIMAL_KHR) {
            result.checkResult("Failed to acquire next swap chain image")
        }
        return Result(imageIndexVar.value, result)
    }

    /**
     * Retrieve the array of presentable images associated with the swapchain.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetSwapchainImagesKHR.html">vkGetSwapchainImagesKHR Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun getImages(): List<Image> {
        val countVar = allocator.alloc<UIntVar>()
        vkGetSwapchainImagesKHR!!(device, handle, countVar.ptr, null)
            .checkResult("Failed to get swap chain image count")

        val count = countVar.value.toLong()
        if (count == 0L) return emptyList()

        val images = allocator.allocArray<VkImageVar>(count)
        vkGetSwapchainImagesKHR!!(device, handle, countVar.ptr, images)
            .checkResult("Failed to get swap chain images")

        return (0 until count).map { Image(device, images[it]!!, destroyable = false) }
    }

    /**
     * Destroy the swapchain.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySwapchainKHR.html">vkDestroySwapchainKHR Manual Page</a>
     */
    override fun close() {
        vkDestroySwapchainKHR!!(device, handle, null)
    }
}
