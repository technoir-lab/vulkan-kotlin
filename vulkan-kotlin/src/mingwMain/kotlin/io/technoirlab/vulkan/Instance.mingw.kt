package io.technoirlab.vulkan

import io.technoirlab.volk.VK_STRUCTURE_TYPE_WIN32_SURFACE_CREATE_INFO_KHR
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.VkWin32SurfaceCreateInfoKHR
import io.technoirlab.volk.vkCreateWin32SurfaceKHR
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.windows.HINSTANCE
import platform.windows.HWND

/**
 * Create a surface for a Win32 native window.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateWin32SurfaceKHR.html">vkCreateWin32SurfaceKHR Manual Page</a>
 */
context(allocator: NativePlacement)
fun Instance.createWin32Surface(hinstance: HINSTANCE, hwnd: HWND): Surface {
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
