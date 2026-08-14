package io.technoirlab.vulkan.presentation

import cnames.structs.ANativeWindow
import io.technoirlab.volk.VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR
import io.technoirlab.volk.VkAndroidSurfaceCreateInfoKHR
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.vkCreateAndroidSurfaceKHR
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

/**
 * Create a surface for an Android native window.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateAndroidSurfaceKHR.html">vkCreateAndroidSurfaceKHR Manual Page</a>
 */
context(allocator: NativePlacement)
fun Instance.createAndroidSurface(nativeWindow: CPointer<ANativeWindow>): Surface {
    val surfaceCreateInfo = allocator.alloc<VkAndroidSurfaceCreateInfoKHR> {
        sType = VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR
        window = nativeWindow
    }
    val surfaceVar = allocator.alloc<VkSurfaceKHRVar>()
    vkCreateAndroidSurfaceKHR!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create Android surface")
    return Surface(handle, surfaceVar.value!!)
}
