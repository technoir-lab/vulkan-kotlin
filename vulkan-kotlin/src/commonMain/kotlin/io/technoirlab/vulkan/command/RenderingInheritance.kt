package io.technoirlab.vulkan.command

import io.technoirlab.volk.VK_ATTACHMENT_UNUSED
import io.technoirlab.volk.VK_FORMAT_UNDEFINED
import io.technoirlab.volk.VK_SAMPLE_COUNT_1_BIT
import io.technoirlab.volk.VkFormat
import io.technoirlab.volk.VkRenderingFlags
import io.technoirlab.volk.VkSampleCountFlagBits

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

/**
 * The dynamic rendering state inherited by a secondary command buffer.
 *
 * Attachment formats and sample count must be compatible with the rendering instance that executes the command buffer.
 * Multiview is unsupported, so the native view mask is always zero.
 *
 * @property colorAttachmentFormats Color attachment formats in rendering attachment order.
 * @property depthAttachmentFormat Depth attachment format, or [VK_FORMAT_UNDEFINED] when unused.
 * @property stencilAttachmentFormat Stencil attachment format, or [VK_FORMAT_UNDEFINED] when unused.
 * @property rasterizationSamples Number of rasterization samples.
 * @property flags Rendering flags inherited from the rendering instance.
 * @property attachmentMappings Local-read output and input mappings. Null uses Vulkan's default mappings.
 */
data class RenderingInheritance(
    val colorAttachmentFormats: List<VkFormat> = emptyList(),
    val depthAttachmentFormat: VkFormat = VK_FORMAT_UNDEFINED,
    val stencilAttachmentFormat: VkFormat = VK_FORMAT_UNDEFINED,
    val rasterizationSamples: VkSampleCountFlagBits = VK_SAMPLE_COUNT_1_BIT,
    val flags: VkRenderingFlags = 0u,
    val attachmentMappings: RenderingAttachmentMappings? = null,
)
