package io.technoirlab.vulkan.command

import io.technoirlab.volk.VkClearAttachment
import io.technoirlab.volk.VkImageAspectFlags

/**
 * An attachment to clear inside a rendering instance.
 *
 * @property aspectMask The color, depth, or stencil aspects to clear.
 * @property clearValue The clear value matching the selected aspects and attachment format.
 * @property colorAttachment The color attachment index, ignored for depth/stencil clears.
 */
data class ClearAttachment(
    val aspectMask: VkImageAspectFlags,
    val clearValue: ClearValue,
    val colorAttachment: UInt = 0u,
)

internal inline fun VkClearAttachment.from(attachment: ClearAttachment) {
    aspectMask = attachment.aspectMask
    colorAttachment = attachment.colorAttachment
    clearValue.from(attachment.clearValue)
}
