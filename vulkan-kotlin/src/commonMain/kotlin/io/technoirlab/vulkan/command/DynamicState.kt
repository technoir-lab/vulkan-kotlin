package io.technoirlab.vulkan.command

import io.technoirlab.volk.VK_DEPTH_CLAMP_MODE_USER_DEFINED_RANGE_EXT
import io.technoirlab.volk.VK_FRAGMENT_SHADING_RATE_COMBINER_OP_KEEP_KHR
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SAMPLE_LOCATIONS_INFO_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_VERTEX_INPUT_ATTRIBUTE_DESCRIPTION_2_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_VERTEX_INPUT_BINDING_DESCRIPTION_2_EXT
import io.technoirlab.volk.VkColorBlendAdvancedEXT
import io.technoirlab.volk.VkColorBlendEquationEXT
import io.technoirlab.volk.VkColorComponentFlags
import io.technoirlab.volk.VkConservativeRasterizationModeEXT
import io.technoirlab.volk.VkDepthClampModeEXT
import io.technoirlab.volk.VkDepthClampRangeEXT
import io.technoirlab.volk.VkExtent2D
import io.technoirlab.volk.VkFragmentShadingRateCombinerOpKHR
import io.technoirlab.volk.VkFragmentShadingRateCombinerOpKHRVar
import io.technoirlab.volk.VkLineRasterizationModeEXT
import io.technoirlab.volk.VkLogicOp
import io.technoirlab.volk.VkPolygonMode
import io.technoirlab.volk.VkProvokingVertexModeEXT
import io.technoirlab.volk.VkSampleCountFlagBits
import io.technoirlab.volk.VkSampleLocationEXT
import io.technoirlab.volk.VkSampleLocationsInfoEXT
import io.technoirlab.volk.VkTessellationDomainOrigin
import io.technoirlab.volk.VkVertexInputAttributeDescription2EXT
import io.technoirlab.volk.VkVertexInputBindingDescription2EXT
import io.technoirlab.volk.vkCmdSetAlphaToCoverageEnableEXT
import io.technoirlab.volk.vkCmdSetAlphaToOneEnableEXT
import io.technoirlab.volk.vkCmdSetColorBlendAdvancedEXT
import io.technoirlab.volk.vkCmdSetColorBlendEnableEXT
import io.technoirlab.volk.vkCmdSetColorBlendEquationEXT
import io.technoirlab.volk.vkCmdSetColorWriteEnableEXT
import io.technoirlab.volk.vkCmdSetColorWriteMaskEXT
import io.technoirlab.volk.vkCmdSetConservativeRasterizationModeEXT
import io.technoirlab.volk.vkCmdSetDepthBounds
import io.technoirlab.volk.vkCmdSetDepthBoundsTestEnable
import io.technoirlab.volk.vkCmdSetDepthClampEnableEXT
import io.technoirlab.volk.vkCmdSetDepthClampRangeEXT
import io.technoirlab.volk.vkCmdSetDepthClipEnableEXT
import io.technoirlab.volk.vkCmdSetDepthClipNegativeOneToOneEXT
import io.technoirlab.volk.vkCmdSetExtraPrimitiveOverestimationSizeEXT
import io.technoirlab.volk.vkCmdSetFragmentShadingRateKHR
import io.technoirlab.volk.vkCmdSetLineRasterizationModeEXT
import io.technoirlab.volk.vkCmdSetLineStipple
import io.technoirlab.volk.vkCmdSetLineStippleEnableEXT
import io.technoirlab.volk.vkCmdSetLogicOpEXT
import io.technoirlab.volk.vkCmdSetLogicOpEnableEXT
import io.technoirlab.volk.vkCmdSetPatchControlPointsEXT
import io.technoirlab.volk.vkCmdSetPolygonModeEXT
import io.technoirlab.volk.vkCmdSetProvokingVertexModeEXT
import io.technoirlab.volk.vkCmdSetRasterizationSamplesEXT
import io.technoirlab.volk.vkCmdSetRasterizationStreamEXT
import io.technoirlab.volk.vkCmdSetSampleLocationsEXT
import io.technoirlab.volk.vkCmdSetSampleLocationsEnableEXT
import io.technoirlab.volk.vkCmdSetSampleMaskEXT
import io.technoirlab.volk.vkCmdSetTessellationDomainOriginEXT
import io.technoirlab.volk.vkCmdSetVertexInputEXT
import io.technoirlab.vulkan.Extent2D
import io.technoirlab.vulkan.internal.toVkBool32
import io.technoirlab.vulkan.pipeline.ColorBlendAdvanced
import io.technoirlab.vulkan.pipeline.ColorBlendEquation
import io.technoirlab.vulkan.pipeline.SampleLocation
import io.technoirlab.vulkan.pipeline.VertexInputAttribute
import io.technoirlab.vulkan.pipeline.VertexInputBinding
import io.technoirlab.vulkan.pipeline.from
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.assert

/**
 * Enable or disable alpha-to-coverage multisampling state dynamically.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetAlphaToCoverageEnableEXT.html">vkCmdSetAlphaToCoverageEnableEXT Manual Page</a>
 */
fun CommandBuffer.setAlphaToCoverageEnable(enable: Boolean) {
    vkCmdSetAlphaToCoverageEnableEXT!!(handle, enable.toVkBool32())
}

/**
 * Enable or disable alpha-to-one multisampling state dynamically.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * Additionally requires the `alphaToOne` feature to be enabled on the device when [enable] is `true`.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetAlphaToOneEnableEXT.html">vkCmdSetAlphaToOneEnableEXT Manual Page</a>
 */
fun CommandBuffer.setAlphaToOneEnable(enable: Boolean) {
    vkCmdSetAlphaToOneEnableEXT!!(handle, enable.toVkBool32())
}

/**
 * Set advanced color blending state dynamically for a range of attachments.
 *
 * Requires `VK_EXT_blend_operation_advanced` together with `VK_EXT_shader_object` or
 * `VK_EXT_extended_dynamic_state3` and the corresponding feature.
 *
 * @param firstAttachment First color attachment affected.
 * @param settings Advanced blending parameters for each affected attachment.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetColorBlendAdvancedEXT.html">vkCmdSetColorBlendAdvancedEXT Manual Page</a>
 */
fun CommandBuffer.setColorBlendAdvanced(firstAttachment: UInt, settings: List<ColorBlendAdvanced>): Unit = memScoped {
    assert(settings.isNotEmpty()) { "settings must not be empty" }
    val values = allocArray<VkColorBlendAdvancedEXT>(settings.size) { index ->
        from(settings[index])
    }
    vkCmdSetColorBlendAdvancedEXT!!(handle, firstAttachment, settings.size.toUInt(), values)
}

/**
 * Enable or disable color blending dynamically for a range of attachments.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * @param firstAttachment First color attachment affected.
 * @param enables Color blend enable state for each affected attachment.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetColorBlendEnableEXT.html">vkCmdSetColorBlendEnableEXT Manual Page</a>
 */
fun CommandBuffer.setColorBlendEnable(firstAttachment: UInt, enables: List<Boolean>): Unit = memScoped {
    assert(enables.isNotEmpty()) { "enables must not be empty" }
    val values = allocArray<UIntVar>(enables.size) { value = enables[it].toVkBool32() }
    vkCmdSetColorBlendEnableEXT!!(handle, firstAttachment, enables.size.toUInt(), values)
}

/**
 * Set color blend equations dynamically for a range of attachments.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * Using a `SRC1` blend factor requires the `dualSrcBlend` feature to be enabled on the device.
 *
 * @param firstAttachment First color attachment affected.
 * @param equations Blend equations for each affected attachment.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetColorBlendEquationEXT.html">vkCmdSetColorBlendEquationEXT Manual Page</a>
 */
fun CommandBuffer.setColorBlendEquation(firstAttachment: UInt, equations: List<ColorBlendEquation>): Unit = memScoped {
    assert(equations.isNotEmpty()) { "equations must not be empty" }
    val values = allocArray<VkColorBlendEquationEXT>(equations.size) { index ->
        from(equations[index])
    }
    vkCmdSetColorBlendEquationEXT!!(handle, firstAttachment, equations.size.toUInt(), values)
}

/**
 * Enable or disable color writes dynamically for attachments starting at attachment zero.
 *
 * Requires the `VK_EXT_color_write_enable` extension and its `colorWriteEnable` feature.
 * When drawing with shader objects and this feature enabled, set a value for every active color attachment.
 *
 * @param enables Color write enable state for each attachment.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetColorWriteEnableEXT.html">vkCmdSetColorWriteEnableEXT Manual Page</a>
 */
fun CommandBuffer.setColorWriteEnable(enables: List<Boolean>): Unit = memScoped {
    assert(enables.isNotEmpty()) { "enables must not be empty" }
    val values = allocArray<UIntVar>(enables.size) { value = enables[it].toVkBool32() }
    vkCmdSetColorWriteEnableEXT!!(handle, enables.size.toUInt(), values)
}

/**
 * Set color component write masks dynamically for a range of attachments.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * @param firstAttachment First color attachment affected.
 * @param masks Color component write mask for each affected attachment.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetColorWriteMaskEXT.html">vkCmdSetColorWriteMaskEXT Manual Page</a>
 */
fun CommandBuffer.setColorWriteMask(firstAttachment: UInt, masks: List<VkColorComponentFlags>): Unit = memScoped {
    assert(masks.isNotEmpty()) { "masks must not be empty" }
    val values = allocArray<UIntVar>(masks.size) { value = masks[it] }
    vkCmdSetColorWriteMaskEXT!!(handle, firstAttachment, masks.size.toUInt(), values)
}

/**
 * Set conservative rasterization mode dynamically.
 *
 * Requires `VK_EXT_conservative_rasterization` together with `VK_EXT_shader_object` or
 * `VK_EXT_extended_dynamic_state3` and the corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetConservativeRasterizationModeEXT.html">vkCmdSetConservativeRasterizationModeEXT Manual Page</a>
 */
fun CommandBuffer.setConservativeRasterizationMode(mode: VkConservativeRasterizationModeEXT) {
    vkCmdSetConservativeRasterizationModeEXT!!(handle, mode)
}

/**
 * Set the minimum and maximum depth bounds dynamically.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBounds.html">vkCmdSetDepthBounds Manual Page</a>
 */
fun CommandBuffer.setDepthBounds(minDepthBounds: Float, maxDepthBounds: Float) {
    vkCmdSetDepthBounds!!(handle, minDepthBounds, maxDepthBounds)
}

/**
 * Enable or disable depth bounds testing dynamically for the command buffer.
 *
 * Requires the `depthBounds` feature when [enable] is `true`. Disabling the test does not require the feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBoundsTestEnable.html">vkCmdSetDepthBoundsTestEnable Manual Page</a>
 */
fun CommandBuffer.setDepthBoundsTestEnable(enable: Boolean) {
    vkCmdSetDepthBoundsTestEnable!!(handle, enable.toVkBool32())
}

/**
 * Enable or disable depth clamping dynamically.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * Additionally requires the `depthClamp` feature to be enabled on the device when [enable] is `true`.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthClampEnableEXT.html">vkCmdSetDepthClampEnableEXT Manual Page</a>
 */
fun CommandBuffer.setDepthClampEnable(enable: Boolean) {
    vkCmdSetDepthClampEnableEXT!!(handle, enable.toVkBool32())
}

/**
 * Set a viewport depth clamp mode that derives its range from viewport state.
 *
 * Requires the `VK_EXT_depth_clamp_control` extension.
 *
 * @param mode Mode used to determine the depth clamp range, excluding `VK_DEPTH_CLAMP_MODE_USER_DEFINED_RANGE_EXT`.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthClampRangeEXT.html">vkCmdSetDepthClampRangeEXT Manual Page</a>
 */
fun CommandBuffer.setDepthClampRange(mode: VkDepthClampModeEXT) {
    assert(mode != VK_DEPTH_CLAMP_MODE_USER_DEFINED_RANGE_EXT) {
        "use the overload with minimum and maximum bounds for VK_DEPTH_CLAMP_MODE_USER_DEFINED_RANGE_EXT"
    }
    vkCmdSetDepthClampRangeEXT!!(handle, mode, null)
}

/**
 * Set a user-defined viewport depth clamp range dynamically.
 *
 * Requires the `VK_EXT_depth_clamp_control` extension.
 *
 * @param minDepthClamp The minimum depth clamp value.
 * @param maxDepthClamp The maximum depth clamp value, greater than or equal to [minDepthClamp].
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthClampRangeEXT.html">vkCmdSetDepthClampRangeEXT Manual Page</a>
 */
fun CommandBuffer.setDepthClampRange(minDepthClamp: Float, maxDepthClamp: Float): Unit = memScoped {
    assert(minDepthClamp <= maxDepthClamp) { "minDepthClamp must be less than or equal to maxDepthClamp" }
    val range = alloc<VkDepthClampRangeEXT> {
        this.minDepthClamp = minDepthClamp
        this.maxDepthClamp = maxDepthClamp
    }
    vkCmdSetDepthClampRangeEXT!!(handle, VK_DEPTH_CLAMP_MODE_USER_DEFINED_RANGE_EXT, range.ptr)
}

/**
 * Enable or disable depth clipping dynamically.
 *
 * Requires `VK_EXT_depth_clip_enable` together with `VK_EXT_shader_object` or
 * `VK_EXT_extended_dynamic_state3` and the corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthClipEnableEXT.html">vkCmdSetDepthClipEnableEXT Manual Page</a>
 */
fun CommandBuffer.setDepthClipEnable(enable: Boolean) {
    vkCmdSetDepthClipEnableEXT!!(handle, enable.toVkBool32())
}

/**
 * Select zero-to-one or negative-one-to-one depth clipping dynamically.
 *
 * Requires `VK_EXT_depth_clip_control` together with `VK_EXT_shader_object` or
 * `VK_EXT_extended_dynamic_state3` and the corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthClipNegativeOneToOneEXT.html">vkCmdSetDepthClipNegativeOneToOneEXT Manual Page</a>
 */
fun CommandBuffer.setDepthClipNegativeOneToOne(negativeOneToOne: Boolean) {
    vkCmdSetDepthClipNegativeOneToOneEXT!!(handle, negativeOneToOne.toVkBool32())
}

/**
 * Set the extra primitive overestimation size dynamically.
 *
 * Requires `VK_EXT_conservative_rasterization` together with `VK_EXT_shader_object` or
 * `VK_EXT_extended_dynamic_state3` and the corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetExtraPrimitiveOverestimationSizeEXT.html">vkCmdSetExtraPrimitiveOverestimationSizeEXT Manual Page</a>
 */
fun CommandBuffer.setExtraPrimitiveOverestimationSize(size: Float) {
    vkCmdSetExtraPrimitiveOverestimationSizeEXT!!(handle, size)
}

/**
 * Set the fragment shading rate and combiner operations dynamically.
 *
 * Requires the `VK_KHR_fragment_shading_rate` extension and at least one of its
 * `pipelineFragmentShadingRate`, `primitiveFragmentShadingRate`, or `attachmentFragmentShadingRate` features.
 * A fragment size other than 1 by 1 requires `pipelineFragmentShadingRate`. Without `primitiveFragmentShadingRate`
 * or `attachmentFragmentShadingRate`, the corresponding combiner operation must be `VK_FRAGMENT_SHADING_RATE_COMBINER_OP_KEEP_KHR`.
 * Applies to shader objects and pipelines with dynamic fragment shading rate enabled.
 *
 * @param fragmentSize Fragment width and height in pixels, each equal to 1, 2, or 4.
 * @param primitiveCombinerOp Operation combining the pipeline and primitive shading rates.
 * @param attachmentCombinerOp Operation combining that result with the attachment shading rate.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetFragmentShadingRateKHR.html">vkCmdSetFragmentShadingRateKHR Manual Page</a>
 */
fun CommandBuffer.setFragmentShadingRate(
    fragmentSize: Extent2D,
    primitiveCombinerOp: VkFragmentShadingRateCombinerOpKHR = VK_FRAGMENT_SHADING_RATE_COMBINER_OP_KEEP_KHR,
    attachmentCombinerOp: VkFragmentShadingRateCombinerOpKHR = VK_FRAGMENT_SHADING_RATE_COMBINER_OP_KEEP_KHR,
): Unit = memScoped {
    assert(fragmentSize.width == 1u || fragmentSize.width == 2u || fragmentSize.width == 4u) {
        "fragment width must be 1, 2, or 4"
    }
    assert(fragmentSize.height == 1u || fragmentSize.height == 2u || fragmentSize.height == 4u) {
        "fragment height must be 1, 2, or 4"
    }
    val size = alloc<VkExtent2D> {
        width = fragmentSize.width
        height = fragmentSize.height
    }
    val combinerOps = allocArray<VkFragmentShadingRateCombinerOpKHRVar>(2) { index: Int ->
        value = if (index == 0) primitiveCombinerOp else attachmentCombinerOp
    }
    vkCmdSetFragmentShadingRateKHR!!(handle, size.ptr, combinerOps)
}

/**
 * Set the line rasterization mode dynamically.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature, or
 * `VK_EXT_extended_dynamic_state3` and its `extendedDynamicState3LineRasterizationMode` feature.
 * Line rasterization is provided by the required Vulkan 1.4 core API.
 *
 * Selecting `VK_LINE_RASTERIZATION_MODE_RECTANGULAR`, `VK_LINE_RASTERIZATION_MODE_BRESENHAM`, or
 * `VK_LINE_RASTERIZATION_MODE_RECTANGULAR_SMOOTH` additionally requires the `rectangularLines`,
 * `bresenhamLines`, or `smoothLines` feature, respectively, to be enabled on the device.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLineRasterizationModeEXT.html">vkCmdSetLineRasterizationModeEXT Manual Page</a>
 */
fun CommandBuffer.setLineRasterizationMode(mode: VkLineRasterizationModeEXT) {
    vkCmdSetLineRasterizationModeEXT!!(handle, mode)
}

/**
 * Set the line stipple repeat factor and bit pattern dynamically.
 *
 * Requires the matching `stippledRectangularLines`, `stippledBresenhamLines`, or `stippledSmoothLines` feature
 * when using stippled lines with the selected line rasterization mode. Setting the repeat factor and bit pattern
 * uses the Vulkan 1.4 core API and does not itself require a stippling feature.
 *
 * @param factor Repeat factor in the range from 1 through 256.
 * @param pattern The 16-bit line stipple pattern.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLineStipple.html">vkCmdSetLineStipple Manual Page</a>
 */
fun CommandBuffer.setLineStipple(factor: UInt, pattern: UShort) {
    assert(factor in 1u..256u) { "factor must be between 1 and 256" }
    vkCmdSetLineStipple!!(handle, factor, pattern)
}

/**
 * Enable or disable line stippling dynamically.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature, or
 * `VK_EXT_extended_dynamic_state3` and its `extendedDynamicState3LineStippleEnable` feature.
 * Line rasterization is provided by the required Vulkan 1.4 core API.
 * Configure the repeat factor and bit pattern with [CommandBuffer.setLineStipple].
 *
 * Using stippled lines requires the matching `stippledRectangularLines`, `stippledBresenhamLines`, or
 * `stippledSmoothLines` feature to be enabled on the device for the selected rasterization mode.
 * The default mode requires `stippledRectangularLines` and the `strictLines` property to be `VK_TRUE`.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLineStippleEnableEXT.html">vkCmdSetLineStippleEnableEXT Manual Page</a>
 */
fun CommandBuffer.setLineStippleEnable(enable: Boolean) {
    vkCmdSetLineStippleEnableEXT!!(handle, enable.toVkBool32())
}

/**
 * Set the logical pixel operation dynamically.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature, or
 * `VK_EXT_extended_dynamic_state2` and its `extendedDynamicState2LogicOp` feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLogicOpEXT.html">vkCmdSetLogicOpEXT Manual Page</a>
 */
fun CommandBuffer.setLogicOp(logicOp: VkLogicOp) {
    vkCmdSetLogicOpEXT!!(handle, logicOp)
}

/**
 * Enable or disable logical pixel operations dynamically.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * Additionally requires the `logicOp` feature to be enabled on the device when [enable] is `true`.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLogicOpEnableEXT.html">vkCmdSetLogicOpEnableEXT Manual Page</a>
 */
fun CommandBuffer.setLogicOpEnable(enable: Boolean) {
    vkCmdSetLogicOpEnableEXT!!(handle, enable.toVkBool32())
}

/**
 * Set the number of control points per patch dynamically.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature, or
 * `VK_EXT_extended_dynamic_state2` and its `extendedDynamicState2PatchControlPoints` feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPatchControlPointsEXT.html">vkCmdSetPatchControlPointsEXT Manual Page</a>
 */
fun CommandBuffer.setPatchControlPoints(patchControlPoints: UInt) {
    vkCmdSetPatchControlPointsEXT!!(handle, patchControlPoints)
}

/**
 * Set polygon mode dynamically for the command buffer.
 *
 * Requires the `VK_EXT_extended_dynamic_state3` extension and its `extendedDynamicState3PolygonMode` feature,
 * or `VK_EXT_shader_object` and its `shaderObject` feature.
 * Line and point modes additionally require `fillModeNonSolid`; rectangle fill requires `VK_NV_fill_rectangle`.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPolygonModeEXT.html">vkCmdSetPolygonModeEXT Manual Page</a>
 */
fun CommandBuffer.setPolygonMode(polygonMode: VkPolygonMode) {
    vkCmdSetPolygonModeEXT!!(handle, polygonMode)
}

/**
 * Set the provoking vertex mode dynamically.
 *
 * Requires `VK_EXT_provoking_vertex` together with `VK_EXT_shader_object` or
 * `VK_EXT_extended_dynamic_state3` and the corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetProvokingVertexModeEXT.html">vkCmdSetProvokingVertexModeEXT Manual Page</a>
 */
fun CommandBuffer.setProvokingVertexMode(mode: VkProvokingVertexModeEXT) {
    vkCmdSetProvokingVertexModeEXT!!(handle, mode)
}

/**
 * Set the rasterization sample count dynamically.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetRasterizationSamplesEXT.html">vkCmdSetRasterizationSamplesEXT Manual Page</a>
 */
fun CommandBuffer.setRasterizationSamples(samples: VkSampleCountFlagBits) {
    vkCmdSetRasterizationSamplesEXT!!(handle, samples)
}

/**
 * Set the rasterization stream dynamically.
 *
 * Requires `VK_EXT_transform_feedback` together with `VK_EXT_shader_object` or
 * `VK_EXT_extended_dynamic_state3` and the corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetRasterizationStreamEXT.html">vkCmdSetRasterizationStreamEXT Manual Page</a>
 */
fun CommandBuffer.setRasterizationStream(stream: UInt) {
    vkCmdSetRasterizationStreamEXT!!(handle, stream)
}

/**
 * Set custom sample locations dynamically.
 *
 * Requires the `VK_EXT_sample_locations` extension.
 *
 * @param samples Number of sample locations per pixel.
 * @param gridSize Dimensions of the sample location grid.
 * @param sampleLocations Locations ordered by pixel in row-major order, then by sample index within each pixel.
 * The list size must equal [samples] multiplied by the grid width and height.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetSampleLocationsEXT.html">vkCmdSetSampleLocationsEXT Manual Page</a>
 */
fun CommandBuffer.setSampleLocations(samples: VkSampleCountFlagBits, gridSize: Extent2D, sampleLocations: List<SampleLocation>): Unit =
    memScoped {
        val pixelCount = gridSize.width.toULong() * gridSize.height.toULong()
        assert(
            samples > 0u && pixelCount <= Int.MAX_VALUE.toULong() &&
                sampleLocations.size.toULong() == samples.toULong() * pixelCount,
        ) { "sampleLocations must contain samples times grid width times grid height entries" }
        val locations = if (sampleLocations.isNotEmpty()) {
            allocArray<VkSampleLocationEXT>(sampleLocations.size) { index ->
                x = sampleLocations[index].x
                y = sampleLocations[index].y
            }
        } else {
            null
        }
        val sampleLocationsInfo = alloc<VkSampleLocationsInfoEXT> {
            sType = VK_STRUCTURE_TYPE_SAMPLE_LOCATIONS_INFO_EXT
            pNext = null
            sampleLocationsPerPixel = samples
            sampleLocationGridSize.width = gridSize.width
            sampleLocationGridSize.height = gridSize.height
            sampleLocationsCount = sampleLocations.size.toUInt()
            pSampleLocations = locations
        }
        vkCmdSetSampleLocationsEXT!!(handle, sampleLocationsInfo.ptr)
    }

/**
 * Enable or disable custom sample locations dynamically.
 *
 * Requires `VK_EXT_sample_locations` together with `VK_EXT_shader_object` or
 * `VK_EXT_extended_dynamic_state3` and the corresponding feature.
 * Configure the sample locations with [CommandBuffer.setSampleLocations].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetSampleLocationsEnableEXT.html">vkCmdSetSampleLocationsEnableEXT Manual Page</a>
 */
fun CommandBuffer.setSampleLocationsEnable(enable: Boolean) {
    vkCmdSetSampleLocationsEnableEXT!!(handle, enable.toVkBool32())
}

/**
 * Set the multisample mask dynamically.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * @param samples Rasterization sample count described by [sampleMasks].
 * @param sampleMasks One 32-bit mask per group of 32 samples.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetSampleMaskEXT.html">vkCmdSetSampleMaskEXT Manual Page</a>
 */
fun CommandBuffer.setSampleMask(samples: VkSampleCountFlagBits, sampleMasks: List<UInt>): Unit = memScoped {
    assert(sampleMasks.isNotEmpty()) { "sampleMasks must not be empty" }
    assert(sampleMasks.size == ((samples + 31u) / 32u).toInt()) {
        "sampleMasks must contain one mask per group of 32 samples"
    }
    val masks = allocArray<UIntVar>(sampleMasks.size) { value = sampleMasks[it] }
    vkCmdSetSampleMaskEXT!!(handle, samples, masks)
}

/**
 * Set the tessellation domain origin dynamically.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetTessellationDomainOriginEXT.html">vkCmdSetTessellationDomainOriginEXT Manual Page</a>
 */
fun CommandBuffer.setTessellationDomainOrigin(origin: VkTessellationDomainOrigin) {
    vkCmdSetTessellationDomainOriginEXT!!(handle, origin)
}

/**
 * Set vertex input binding and attribute descriptions dynamically.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature, or
 * `VK_EXT_vertex_input_dynamic_state` and its `vertexInputDynamicState` feature.
 *
 * @param bindings Vertex buffer binding descriptions.
 * @param attributes Vertex attribute descriptions.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetVertexInputEXT.html">vkCmdSetVertexInputEXT Manual Page</a>
 */
fun CommandBuffer.setVertexInput(bindings: List<VertexInputBinding>, attributes: List<VertexInputAttribute>): Unit = memScoped {
    val bindingDescriptions = if (bindings.isNotEmpty()) {
        allocArray<VkVertexInputBindingDescription2EXT>(bindings.size) { index ->
            sType = VK_STRUCTURE_TYPE_VERTEX_INPUT_BINDING_DESCRIPTION_2_EXT
            pNext = null
            from(bindings[index])
        }
    } else {
        null
    }
    val attributeDescriptions = if (attributes.isNotEmpty()) {
        allocArray<VkVertexInputAttributeDescription2EXT>(attributes.size) { index ->
            sType = VK_STRUCTURE_TYPE_VERTEX_INPUT_ATTRIBUTE_DESCRIPTION_2_EXT
            pNext = null
            from(attributes[index])
        }
    } else {
        null
    }
    vkCmdSetVertexInputEXT!!(
        handle,
        bindings.size.toUInt(),
        bindingDescriptions,
        attributes.size.toUInt(),
        attributeDescriptions,
    )
}
