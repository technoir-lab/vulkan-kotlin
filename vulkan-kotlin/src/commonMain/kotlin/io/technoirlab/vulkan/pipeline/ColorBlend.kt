package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VkBlendFactor
import io.technoirlab.volk.VkBlendOp
import io.technoirlab.volk.VkBlendOverlapEXT
import io.technoirlab.volk.VkColorBlendAdvancedEXT
import io.technoirlab.volk.VkColorBlendEquationEXT
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

/**
 * Color and alpha blending parameters for an attachment.
 *
 * @property srcColorBlendFactor The source color blend factor.
 * @property dstColorBlendFactor The destination color blend factor.
 * @property colorBlendOp The operation combining the color components.
 * @property srcAlphaBlendFactor The source alpha blend factor.
 * @property dstAlphaBlendFactor The destination alpha blend factor.
 * @property alphaBlendOp The operation combining the alpha components.
 */
data class ColorBlendEquation(
    val srcColorBlendFactor: VkBlendFactor,
    val dstColorBlendFactor: VkBlendFactor,
    val colorBlendOp: VkBlendOp,
    val srcAlphaBlendFactor: VkBlendFactor,
    val dstAlphaBlendFactor: VkBlendFactor,
    val alphaBlendOp: VkBlendOp,
)

internal inline fun VkColorBlendAdvancedEXT.from(settings: ColorBlendAdvanced) {
    advancedBlendOp = settings.advancedBlendOp
    srcPremultiplied = settings.srcPremultiplied.toVkBool32()
    dstPremultiplied = settings.dstPremultiplied.toVkBool32()
    blendOverlap = settings.blendOverlap
    clampResults = settings.clampResults.toVkBool32()
}

internal inline fun VkColorBlendEquationEXT.from(equation: ColorBlendEquation) {
    srcColorBlendFactor = equation.srcColorBlendFactor
    dstColorBlendFactor = equation.dstColorBlendFactor
    colorBlendOp = equation.colorBlendOp
    srcAlphaBlendFactor = equation.srcAlphaBlendFactor
    dstAlphaBlendFactor = equation.dstAlphaBlendFactor
    alphaBlendOp = equation.alphaBlendOp
}
