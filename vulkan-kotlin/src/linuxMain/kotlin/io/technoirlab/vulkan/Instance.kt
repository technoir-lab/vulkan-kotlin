package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_WAYLAND_SURFACE_CREATE_INFO_KHR
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.VkWaylandSurfaceCreateInfoKHR
import io.technoirlab.volk.vkCreateWaylandSurfaceKHR
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Create a surface for a Wayland window.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateWaylandSurfaceKHR.html">vkCreateWaylandSurfaceKHR Manual Page</a>
 */
context(allocator: NativePlacement)
fun Instance.createWaylandSurface(createInfo: VkWaylandSurfaceCreateInfoKHR.() -> Unit): Surface {
    val surfaceCreateInfo = allocator.alloc<VkWaylandSurfaceCreateInfoKHR> {
        sType = VK_STRUCTURE_TYPE_WAYLAND_SURFACE_CREATE_INFO_KHR
        createInfo()
    }
    val surfaceVar = allocator.alloc<VkSurfaceKHRVar>()
    vkCreateWaylandSurfaceKHR!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create a Wayland surface")
    return Surface(handle, surfaceVar.value!!)
}
