package io.technoirlab.vulkan

import io.technoirlab.volk.VK_SEMAPHORE_TYPE_TIMELINE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SEMAPHORE_SIGNAL_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SEMAPHORE_WAIT_INFO
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkSemaphore
import io.technoirlab.volk.VkSemaphoreSignalInfo
import io.technoirlab.volk.VkSemaphoreType
import io.technoirlab.volk.VkSemaphoreWaitInfo
import io.technoirlab.volk.vkDestroySemaphore
import io.technoirlab.volk.vkGetSemaphoreCounterValue
import io.technoirlab.volk.vkSignalSemaphore
import io.technoirlab.volk.vkWaitSemaphores
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.time.Duration

/**
 * Wrapper for [VkSemaphore].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkSemaphore.html">VkSemaphore Manual Page</a>
 */
class Semaphore(
    private val device: VkDevice,
    override val handle: VkSemaphore,
    val semaphoreType: VkSemaphoreType
) : Object<VkSemaphore> {

    /**
     * Get the current counter value of the timeline semaphore.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetSemaphoreCounterValue.html">vkGetSemaphoreCounterValue Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun counterValue(): ULong {
        require(semaphoreType == VK_SEMAPHORE_TYPE_TIMELINE) { "Not a timeline semaphore" }

        val valueVar = allocator.alloc<ULongVar>()
        vkGetSemaphoreCounterValue!!(device, handle, valueVar.ptr)
            .checkResult("Failed to get timeline semaphore counter value")
        return valueVar.value
    }

    /**
     * Signal the timeline semaphore on the host.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkSignalSemaphore.html">vkSignalSemaphore Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun signal(value: ULong) {
        require(semaphoreType == VK_SEMAPHORE_TYPE_TIMELINE) { "Not a timeline semaphore" }

        val signalInfo = allocator.alloc<VkSemaphoreSignalInfo> {
            sType = VK_STRUCTURE_TYPE_SEMAPHORE_SIGNAL_INFO
            semaphore = handle
            this.value = value
        }
        vkSignalSemaphore!!(device, signalInfo.ptr)
            .checkResult("Failed to signal timeline semaphore")
    }

    /**
     * Wait for the timeline semaphore to reach at least the given value.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkWaitSemaphores.html">vkWaitSemaphores Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun wait(value: ULong, timeout: Duration = Duration.INFINITE) {
        require(semaphoreType == VK_SEMAPHORE_TYPE_TIMELINE) { "Not a timeline semaphore" }

        val valueVar = allocator.alloc<ULongVar> { this.value = value }
        val waitInfo = allocator.alloc<VkSemaphoreWaitInfo> {
            sType = VK_STRUCTURE_TYPE_SEMAPHORE_WAIT_INFO
            semaphoreCount = 1u
            pSemaphores = allocator.allocArrayOf(handle)
            pValues = valueVar.ptr
            flags = 0u
        }
        vkWaitSemaphores!!(device, waitInfo.ptr, timeout.inWholeNanosecondsULong)
            .checkResult("Failed to wait for timeline semaphore")
    }

    /**
     * Destroy the semaphore.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySemaphore.html">vkDestroySemaphore Manual Page</a>
     */
    override fun close() {
        vkDestroySemaphore!!(device, handle, null)
    }
}
