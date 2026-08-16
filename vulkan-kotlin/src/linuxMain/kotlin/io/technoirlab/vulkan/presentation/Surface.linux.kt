package io.technoirlab.vulkan.presentation

import cnames.structs.wl_display
import cnames.structs.wl_surface
import io.technoirlab.volk.VK_KHR_SURFACE_EXTENSION_NAME
import io.technoirlab.volk.VK_KHR_WAYLAND_SURFACE_EXTENSION_NAME
import io.technoirlab.volk.VK_STRUCTURE_TYPE_WAYLAND_SURFACE_CREATE_INFO_KHR
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.VkWaylandSurfaceCreateInfoKHR
import io.technoirlab.volk.vkCreateWaylandSurfaceKHR
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Create a surface for a Wayland window.
 * Requires `VK_KHR_surface` and `VK_KHR_wayland_surface` to be enabled on the instance.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateWaylandSurfaceKHR.html">vkCreateWaylandSurfaceKHR Manual Page</a>
 */
context(allocator: NativePlacement)
fun Instance.createWaylandSurface(display: CPointer<wl_display>, surface: CPointer<wl_surface>): Surface {
    assert(VK_KHR_SURFACE_EXTENSION_NAME in enabledExtensions && VK_KHR_WAYLAND_SURFACE_EXTENSION_NAME in enabledExtensions) {
        "Creating a Wayland surface requires VK_KHR_surface and VK_KHR_wayland_surface"
    }
    val surfaceCreateInfo = allocator.alloc<VkWaylandSurfaceCreateInfoKHR> {
        sType = VK_STRUCTURE_TYPE_WAYLAND_SURFACE_CREATE_INFO_KHR
        this.display = display
        this.surface = surface
    }
    val surfaceVar = allocator.alloc<VkSurfaceKHRVar>()
    vkCreateWaylandSurfaceKHR!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create a Wayland surface")
    return Surface(handle, surfaceVar.value!!)
}
