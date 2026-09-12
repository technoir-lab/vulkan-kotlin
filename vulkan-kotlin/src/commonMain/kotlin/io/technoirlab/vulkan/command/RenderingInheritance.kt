package io.technoirlab.vulkan.command

import io.technoirlab.volk.VK_FORMAT_UNDEFINED
import io.technoirlab.volk.VK_SAMPLE_COUNT_1_BIT
import io.technoirlab.volk.VkFormat
import io.technoirlab.volk.VkRenderingFlags
import io.technoirlab.volk.VkSampleCountFlagBits

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
