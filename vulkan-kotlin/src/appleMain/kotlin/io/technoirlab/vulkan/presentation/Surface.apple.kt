package io.technoirlab.vulkan.presentation

import io.technoirlab.volk.VK_STRUCTURE_TYPE_METAL_SURFACE_CREATE_INFO_EXT
import io.technoirlab.volk.VkMetalSurfaceCreateInfoEXT
import io.technoirlab.volk.VkSurfaceKHRVar
import io.technoirlab.volk.vkCreateMetalSurfaceEXT
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.interpretCPointer
import kotlinx.cinterop.invoke
import kotlinx.cinterop.objcPtr
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.QuartzCore.CAMetalLayer

/**
 * Create a surface for CAMetalLayer.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateMetalSurfaceEXT.html">vkCreateMetalSurfaceEXT Manual Page</a>
 */
context(allocator: NativePlacement)
fun Instance.createMetalSurface(layer: CAMetalLayer): Surface {
    val surfaceCreateInfo = allocator.alloc<VkMetalSurfaceCreateInfoEXT> {
        sType = VK_STRUCTURE_TYPE_METAL_SURFACE_CREATE_INFO_EXT
        pLayer = interpretCPointer(layer.objcPtr())
    }
    val surfaceVar = allocator.alloc<VkSurfaceKHRVar>()
    vkCreateMetalSurfaceEXT!!(handle, surfaceCreateInfo.ptr, null, surfaceVar.ptr)
        .checkResult("Failed to create a Metal surface")
    return Surface(handle, surfaceVar.value!!)
}
