package io.technoirlab.vulkan.command

import io.technoirlab.volk.VK_ATTACHMENT_UNUSED

/**
 * The output locations and input attachment indices inherited for dynamic rendering with local read.
 *
 * Non-null color lists must contain one entry per color format in [RenderingInheritance.colorAttachmentFormats].
 * Non-[VK_ATTACHMENT_UNUSED] entries in each list must be unique. Color input indices must not collide with
 * depth or stencil input indices; depth and stencil may share an input index.
 *
 * @property colorAttachmentLocations Fragment output locations in rendering attachment order. Null uses identity mapping.
 * An entry of [VK_ATTACHMENT_UNUSED] disables the corresponding output mapping.
 * @property colorAttachmentInputIndices Shader input attachment indices in rendering attachment order. Null uses identity mapping.
 * An entry of [VK_ATTACHMENT_UNUSED] disables the corresponding input mapping.
 * @property depthInputAttachmentIndex Depth input index. Null selects an input without an `InputAttachmentIndex` decoration;
 * [VK_ATTACHMENT_UNUSED] disables the depth input mapping.
 * @property stencilInputAttachmentIndex Stencil input index. Null selects an input without an `InputAttachmentIndex` decoration;
 * [VK_ATTACHMENT_UNUSED] disables the stencil input mapping.
 */
data class RenderingAttachmentMappings(
    val colorAttachmentLocations: List<UInt>? = null,
    val colorAttachmentInputIndices: List<UInt>? = null,
    val depthInputAttachmentIndex: UInt? = null,
    val stencilInputAttachmentIndex: UInt? = null,
)
