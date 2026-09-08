package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VkBlendOp
import io.technoirlab.volk.VkBlendOverlapEXT
import io.technoirlab.volk.VkColorBlendAdvancedEXT
import io.technoirlab.vulkan.internal.toVkBool32

/**
 * Advanced color blending parameters for an attachment.
 *
 * @property advancedBlendOp The advanced blend operation.
 * @property srcPremultiplied Whether the source color is treated as premultiplied.
 * @property dstPremultiplied Whether the destination color is treated as premultiplied.
 * @property blendOverlap The assumed overlap between source and destination coverage.
 * @property clampResults Whether to clamp results to the range from zero to one.
 */
data class ColorBlendAdvanced(
    val advancedBlendOp: VkBlendOp,
    val srcPremultiplied: Boolean,
    val dstPremultiplied: Boolean,
    val blendOverlap: VkBlendOverlapEXT,
    val clampResults: Boolean,
)

internal inline fun VkColorBlendAdvancedEXT.from(settings: ColorBlendAdvanced) {
    advancedBlendOp = settings.advancedBlendOp
    srcPremultiplied = settings.srcPremultiplied.toVkBool32()
    dstPremultiplied = settings.dstPremultiplied.toVkBool32()
    blendOverlap = settings.blendOverlap
    clampResults = settings.clampResults.toVkBool32()
}
