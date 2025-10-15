package io.technoirlab.vulkan

import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkFence
import io.technoirlab.volk.VkFenceVar
import io.technoirlab.volk.vkDestroyFence
import io.technoirlab.volk.vkResetFences
import io.technoirlab.volk.vkWaitForFences
import kotlinx.cinterop.MemScope
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
class Fence(
    private val device: VkDevice,
    val handle: VkFence
) : AutoCloseable {

    /**
     * Reset the status of the fence from signaled to unsignaled state.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetFences.html">vkResetFences Manual Page</a>
     */
    context(memScope: MemScope)
    fun reset() {
        val fenceVar = memScope.alloc<VkFenceVar> {
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
    context(memScope: MemScope)
    fun wait(timeout: Duration = Duration.INFINITE) {
        val fenceVar = memScope.alloc<VkFenceVar> {
            value = handle
        }
        vkWaitForFences!!(device, 1u, fenceVar.ptr, VK_TRUE, timeout.inWholeNanoseconds.toULong())
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
