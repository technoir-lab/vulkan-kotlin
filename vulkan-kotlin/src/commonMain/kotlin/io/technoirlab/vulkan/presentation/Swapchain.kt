package io.technoirlab.vulkan.presentation

import io.technoirlab.volk.VK_ERROR_OUT_OF_DATE_KHR
import io.technoirlab.volk.VK_INCOMPLETE
import io.technoirlab.volk.VK_NOT_READY
import io.technoirlab.volk.VK_OBJECT_TYPE_SWAPCHAIN_KHR
import io.technoirlab.volk.VK_STRUCTURE_TYPE_ACQUIRE_NEXT_IMAGE_INFO_KHR
import io.technoirlab.volk.VK_SUBOPTIMAL_KHR
import io.technoirlab.volk.VK_TIMEOUT
import io.technoirlab.volk.VkAcquireNextImageInfoKHR
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkImageVar
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkResult
import io.technoirlab.volk.VkSwapchainKHR
import io.technoirlab.volk.vkAcquireNextImage2KHR
import io.technoirlab.volk.vkDestroySwapchainKHR
import io.technoirlab.volk.vkGetSwapchainImagesKHR
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.VulkanResult
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.image.Image
import io.technoirlab.vulkan.internal.inWholeNanosecondsULong
import io.technoirlab.vulkan.sync.Fence
import io.technoirlab.vulkan.sync.Semaphore
import kotlinx.cinterop.CArrayPointer
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.assert
import kotlin.time.Duration

/**
 * Wrapper for [VkSwapchainKHR].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkSwapchainKHR.html">VkSwapchainKHR Manual Page</a>
 */
class Swapchain internal constructor(
    private val device: VkDevice,
    override val handle: VkSwapchainKHR,
) : VulkanObject,
    AutoCloseable {
    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_SWAPCHAIN_KHR

    /**
     * Retrieve the index of the next available presentable image.
     *
     * At least one of [semaphore] and [fence] must be non-null.
     *
     * The payload of the returned result is a valid image index only when its status is `VK_SUCCESS` or
     * `VK_SUBOPTIMAL_KHR`. `VK_TIMEOUT`, `VK_NOT_READY`, and `VK_ERROR_OUT_OF_DATE_KHR` are also returned as the
     * status, but no image is acquired and the payload must be ignored. Any other error throws
     * [io.technoirlab.vulkan.VulkanException].
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAcquireNextImage2KHR.html">vkAcquireNextImage2KHR Manual Page</a>
     */
    fun acquireNextImage(semaphore: Semaphore? = null, fence: Fence? = null, timeout: Duration = Duration.INFINITE): VulkanResult<UInt> =
        memScoped {
            assert(semaphore != null || fence != null) { "At least one of semaphore or fence must be non-null" }
            val acquireInfo = alloc<VkAcquireNextImageInfoKHR> {
                sType = VK_STRUCTURE_TYPE_ACQUIRE_NEXT_IMAGE_INFO_KHR
                deviceMask = 1u
                swapchain = handle
                this.semaphore = semaphore?.handle
                this.fence = fence?.handle
                this.timeout = timeout.inWholeNanosecondsULong
            }
            val imageIndexVar = alloc<UIntVar>()
            val result = vkAcquireNextImage2KHR!!(device, acquireInfo.ptr, imageIndexVar.ptr)
            if (result != VK_ERROR_OUT_OF_DATE_KHR &&
                result != VK_NOT_READY &&
                result != VK_SUBOPTIMAL_KHR &&
                result != VK_TIMEOUT
            ) {
                result.checkResult("Failed to acquire next swap chain image")
            }
            return VulkanResult(imageIndexVar.value, result)
        }

    /**
     * Retrieve the array of presentable images associated with the swapchain.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetSwapchainImagesKHR.html">vkGetSwapchainImagesKHR Manual Page</a>
     */
    fun getImages(): List<Image> = memScoped {
        val countVar = alloc<UIntVar>()
        var images: CArrayPointer<VkImageVar>
        var result: VkResult
        do {
            vkGetSwapchainImagesKHR!!(device, handle, countVar.ptr, null)
                .checkResult("Failed to get swap chain image count")

            val count = countVar.value.toInt()
            if (count == 0) return emptyList()

            images = allocArray<VkImageVar>(count)
            result = vkGetSwapchainImagesKHR!!(device, handle, countVar.ptr, images)
        } while (result == VK_INCOMPLETE)
        result.checkResult("Failed to get swap chain images")

        List(countVar.value.toInt()) { index -> Image(device, images[index]!!, destroyable = false) }
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
