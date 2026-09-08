package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VkBlendFactor
import io.technoirlab.volk.VkBlendOp
import io.technoirlab.volk.VkColorBlendEquationEXT

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

internal inline fun VkColorBlendEquationEXT.from(equation: ColorBlendEquation) {
    srcColorBlendFactor = equation.srcColorBlendFactor
    dstColorBlendFactor = equation.dstColorBlendFactor
    colorBlendOp = equation.colorBlendOp
    srcAlphaBlendFactor = equation.srcAlphaBlendFactor
    dstAlphaBlendFactor = equation.dstAlphaBlendFactor
    alphaBlendOp = equation.alphaBlendOp
}
