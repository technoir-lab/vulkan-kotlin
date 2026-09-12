package io.technoirlab.vulkan.image

import io.technoirlab.volk.VK_RESOLVE_MODE_NONE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_RESOLVE_IMAGE_MODE_INFO_KHR
import io.technoirlab.volk.VkResolveImageFlagsKHR
import io.technoirlab.volk.VkResolveImageModeInfoKHR
import io.technoirlab.volk.VkResolveModeFlagBits

/**
 * Additional resolve controls provided by `VK_KHR_maintenance10`.
 *
 * The extension and its `maintenance10` feature must be enabled when using these controls.
 *
 * @property resolveMode The resolve mode for color or depth aspects, or [VK_RESOLVE_MODE_NONE] to leave them unresolved.
 * @property stencilResolveMode The resolve mode for the stencil aspect, or [VK_RESOLVE_MODE_NONE] to leave it unresolved.
 * @property flags Flags controlling the sRGB transfer function used during the resolve.
 */
data class ResolveImageModeInfo(
    val resolveMode: VkResolveModeFlagBits = VK_RESOLVE_MODE_NONE,
    val stencilResolveMode: VkResolveModeFlagBits = VK_RESOLVE_MODE_NONE,
    val flags: VkResolveImageFlagsKHR = 0u,
)

internal inline fun VkResolveImageModeInfoKHR.from(info: ResolveImageModeInfo) {
    sType = VK_STRUCTURE_TYPE_RESOLVE_IMAGE_MODE_INFO_KHR
    pNext = null
    flags = info.flags
    resolveMode = info.resolveMode
    stencilResolveMode = info.stencilResolveMode
}
