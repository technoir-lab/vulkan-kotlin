package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_SURFACE_KHR
import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkSurfaceKHR
import io.technoirlab.volk.vkDestroySurfaceKHR
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkSurfaceKHR].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkSurfaceKHR.html">VkSurfaceKHR Manual Page</a>
 */
class Surface internal constructor(
    private val instance: VkInstance,
    override val handle: VkSurfaceKHR
) : Object<VkSurfaceKHR> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_SURFACE_KHR

    /**
     * Destroy the surface.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySurfaceKHR.html">vkDestroySurfaceKHR Manual Page</a>
     */
    override fun close() {
        vkDestroySurfaceKHR!!(instance, handle, null)
    }
}
