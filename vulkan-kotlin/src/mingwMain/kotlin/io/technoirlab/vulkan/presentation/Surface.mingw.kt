package io.technoirlab.vulkan.presentation

import io.technoirlab.volk.VK_KHR_SURFACE_EXTENSION_NAME
import io.technoirlab.volk.VK_KHR_WIN32_SURFACE_EXTENSION_NAME
import io.technoirlab.volk.VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.VkWin32SurfaceCreateInfoKHR
import io.technoirlab.volk.vkCreateWin32SurfaceKHR
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.windows.HINSTANCE
import platform.windows.HWND

/**
 * Create a surface for a Win32 native window.
 * Requires `VK_KHR_surface` and `VK_KHR_win32_surface` to be enabled on the instance.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateWin32SurfaceKHR.html">vkCreateWin32SurfaceKHR Manual Page</a>
 */
context(allocator: NativePlacement)
fun Instance.createWin32Surface(hinstance: HINSTANCE, hwnd: HWND): Surface {
    assert(VK_KHR_SURFACE_EXTENSION_NAME in enabledExtensions && VK_KHR_WIN32_SURFACE_EXTENSION_NAME in enabledExtensions) {
        "Creating a Win32 surface requires VK_KHR_surface and VK_KHR_win32_surface"
    }
    val surfaceCreateInfo = allocator.alloc<VkWin32SurfaceCreateInfoKHR> {
        sType = VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR
        this.hinstance = hinstance
        this.hwnd = hwnd
    }
    val surfaceVar = allocator.alloc<VkSurfaceKHRVar>()
    vkCreateWin32SurfaceKHR!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create a Win32 surface")
    return Surface(handle, surfaceVar.value!!)
}
