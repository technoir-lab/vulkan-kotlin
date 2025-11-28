package io.technoirlab.vulkan

import io.technoirlab.volk.VK_NOT_READY
import io.technoirlab.volk.VK_OBJECT_TYPE_FENCE
import io.technoirlab.volk.VK_SUCCESS
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkFence
import io.technoirlab.volk.VkFenceVar
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.vkDestroyFence
import io.technoirlab.volk.vkGetFenceStatus
import io.technoirlab.volk.vkResetFences
import io.technoirlab.volk.vkWaitForFences
import io.technoirlab.vulkan.internal.inWholeNanosecondsULong
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.time.Duration

/**
 * Wrapper for [VkFence].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkFence.html">VkFence Manual Page</a>
 */
class Fence internal constructor(
    private val device: VkDevice,
    override val handle: VkFence
) : Object<VkFence> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_FENCE

    /**
     * Indicates whether the fence is signaled.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetFenceStatus.html">vkGetFenceStatus Manual Page</a>
     */
    val isSignaled: Boolean
        get() = when (val result = vkGetFenceStatus!!(device, handle)) {
            VK_SUCCESS -> true
            VK_NOT_READY -> false
            else -> throw VulkanException(result, "Failed to get fence status")
        }

    /**
     * Reset the status of the fence from signaled to unsignaled state.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetFences.html">vkResetFences Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun reset() {
        val fenceVar = allocator.alloc<VkFenceVar> {
            value = handle
        }
        vkResetFences!!(device, 1u, fenceVar.ptr)
            .checkResult("Failed to reset fence")
    }

    /**
     * Wait for the fence to become signaled.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkWaitForFences.html">vkWaitForFences Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun wait(timeout: Duration = Duration.INFINITE) {
        val fenceVar = allocator.alloc<VkFenceVar> {
            value = handle
        }
        vkWaitForFences!!(device, 1u, fenceVar.ptr, VK_TRUE, timeout.inWholeNanosecondsULong)
            .checkResult("Failed to wait for fence")
    }

    /**
     * Destroy the fence.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyFence.html">vkDestroyFence Manual Page</a>
     */
    override fun close() {
        vkDestroyFence!!(device, handle, null)
    }
}
