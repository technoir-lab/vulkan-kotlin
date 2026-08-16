package io.technoirlab.vulkan.presentation

import io.technoirlab.volk.VK_EXT_HEADLESS_SURFACE_EXTENSION_NAME
import io.technoirlab.volk.VK_KHR_SURFACE_EXTENSION_NAME
import io.technoirlab.volk.VK_OBJECT_TYPE_SURFACE_KHR
import io.technoirlab.volk.VK_STRUCTURE_TYPE_HEADLESS_SURFACE_CREATE_INFO_EXT
import io.technoirlab.volk.VkHeadlessSurfaceCreateInfoEXT
import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkSurfaceKHR
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.vkCreateHeadlessSurfaceEXT
import io.technoirlab.volk.vkDestroySurfaceKHR
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Wrapper for [VkSurfaceKHR].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkSurfaceKHR.html">VkSurfaceKHR Manual Page</a>
 */
class Surface internal constructor(
    private val instance: VkInstance,
    override val handle: VkSurfaceKHR,
) : VulkanObject,
    AutoCloseable {

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

/**
 * Create a headless surface.
 * Requires `VK_KHR_surface` and `VK_EXT_headless_surface` to be enabled on the instance.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateHeadlessSurfaceEXT.html">vkCreateHeadlessSurfaceEXT Manual Page</a>
 */
context(allocator: NativePlacement)
fun Instance.createHeadlessSurface(): Surface {
    assert(VK_KHR_SURFACE_EXTENSION_NAME in enabledExtensions && VK_EXT_HEADLESS_SURFACE_EXTENSION_NAME in enabledExtensions) {
        "Creating a headless surface requires VK_KHR_surface and VK_EXT_headless_surface"
    }
    val surfaceCreateInfo = allocator.alloc<VkHeadlessSurfaceCreateInfoEXT> {
        sType = VK_STRUCTURE_TYPE_HEADLESS_SURFACE_CREATE_INFO_EXT
    }
    val surfaceVar = allocator.alloc<VkSurfaceKHRVar>()
    vkCreateHeadlessSurfaceEXT!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create a headless surface")
    return Surface(handle, surfaceVar.value!!)
}
