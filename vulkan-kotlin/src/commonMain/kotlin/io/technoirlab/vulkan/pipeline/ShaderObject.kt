package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_DEPTH_CLAMP_MODE_USER_DEFINED_RANGE_EXT
import io.technoirlab.volk.VK_INCOMPLETE
import io.technoirlab.volk.VK_OBJECT_TYPE_SHADER_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SHADER_CREATE_INFO_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_VERTEX_INPUT_ATTRIBUTE_DESCRIPTION_2_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_VERTEX_INPUT_BINDING_DESCRIPTION_2_EXT
import io.technoirlab.volk.VK_SUCCESS
import io.technoirlab.volk.VkColorBlendAdvancedEXT
import io.technoirlab.volk.VkColorBlendEquationEXT
import io.technoirlab.volk.VkColorComponentFlags
import io.technoirlab.volk.VkConservativeRasterizationModeEXT
import io.technoirlab.volk.VkDepthClampModeEXT
import io.technoirlab.volk.VkDepthClampRangeEXT
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkLineRasterizationModeEXT
import io.technoirlab.volk.VkLogicOp
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkProvokingVertexModeEXT
import io.technoirlab.volk.VkSampleCountFlagBits
import io.technoirlab.volk.VkShaderCreateInfoEXT
import io.technoirlab.volk.VkShaderEXT
import io.technoirlab.volk.VkShaderEXTVar
import io.technoirlab.volk.VkShaderStageFlagBits
import io.technoirlab.volk.VkTessellationDomainOrigin
import io.technoirlab.volk.VkVertexInputAttributeDescription2EXT
import io.technoirlab.volk.VkVertexInputBindingDescription2EXT
import io.technoirlab.volk.vkCmdBindShadersEXT
import io.technoirlab.volk.vkCmdSetAlphaToCoverageEnableEXT
import io.technoirlab.volk.vkCmdSetAlphaToOneEnableEXT
import io.technoirlab.volk.vkCmdSetColorBlendAdvancedEXT
import io.technoirlab.volk.vkCmdSetColorBlendEnableEXT
import io.technoirlab.volk.vkCmdSetColorBlendEquationEXT
import io.technoirlab.volk.vkCmdSetColorWriteEnableEXT
import io.technoirlab.volk.vkCmdSetColorWriteMaskEXT
import io.technoirlab.volk.vkCmdSetConservativeRasterizationModeEXT
import io.technoirlab.volk.vkCmdSetDepthBounds
import io.technoirlab.volk.vkCmdSetDepthClampEnableEXT
import io.technoirlab.volk.vkCmdSetDepthClampRangeEXT
import io.technoirlab.volk.vkCmdSetDepthClipEnableEXT
import io.technoirlab.volk.vkCmdSetDepthClipNegativeOneToOneEXT
import io.technoirlab.volk.vkCmdSetExtraPrimitiveOverestimationSizeEXT
import io.technoirlab.volk.vkCmdSetLineRasterizationModeEXT
import io.technoirlab.volk.vkCmdSetLineStippleEnableEXT
import io.technoirlab.volk.vkCmdSetLogicOpEXT
import io.technoirlab.volk.vkCmdSetLogicOpEnableEXT
import io.technoirlab.volk.vkCmdSetPatchControlPointsEXT
import io.technoirlab.volk.vkCmdSetProvokingVertexModeEXT
import io.technoirlab.volk.vkCmdSetRasterizationSamplesEXT
import io.technoirlab.volk.vkCmdSetRasterizationStreamEXT
import io.technoirlab.volk.vkCmdSetSampleLocationsEnableEXT
import io.technoirlab.volk.vkCmdSetSampleMaskEXT
import io.technoirlab.volk.vkCmdSetTessellationDomainOriginEXT
import io.technoirlab.volk.vkCmdSetVertexInputEXT
import io.technoirlab.volk.vkCreateShadersEXT
import io.technoirlab.volk.vkDestroyShaderEXT
import io.technoirlab.volk.vkGetShaderBinaryDataEXT
import io.technoirlab.vulkan.Device
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.VulkanResult
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.command.CommandBuffer
import io.technoirlab.vulkan.internal.toVkBool32
import io.technoirlab.vulkan.memory.MemoryRegion
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.assert

/**
 * Wrapper for [VkShaderEXT].
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkShaderEXT.html">VkShaderEXT Manual Page</a>
 */
class Shader internal constructor(
    internal val device: VkDevice,
    override val handle: VkShaderEXT,
) : VulkanObject,
    AutoCloseable {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_SHADER_EXT

    /**
     * Destroy the shader object.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyShaderEXT.html">vkDestroyShaderEXT Manual Page</a>
     */
    override fun close() {
        vkDestroyShaderEXT!!(device, handle, null)
    }
}

/**
 * Bind shader objects to stages in the command buffer.
 *
 * A `null` shader unbinds the corresponding stage. If [shaders] itself is `null`, all specified stages are unbound.
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @param stages Shader stages affected by the binding operation.
 * @param shaders Shader objects to bind, or `null` to unbind every specified stage.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindShadersEXT.html">vkCmdBindShadersEXT Manual Page</a>
 */
fun CommandBuffer.bindShaders(stages: List<VkShaderStageFlagBits>, shaders: List<Shader?>? = null): Unit = memScoped {
    assert(stages.isNotEmpty()) { "stages must not be empty" }
    assert(shaders == null || stages.size == shaders.size) { "stages and shaders must have the same size" }

    val stageValues = allocArray<UIntVar>(stages.size) { value = stages[it] }
    val shaderValues = shaders?.let { shaderList ->
        allocArray<VkShaderEXTVar>(shaderList.size) { index -> value = shaderList[index]?.handle }
    }
    vkCmdBindShadersEXT!!(handle, stages.size.toUInt(), stageValues, shaderValues)
}

/**
 * Create one or more shader objects.
 *
 * Any shaders created before a batch creation failure are destroyed before the failure is reported.
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @param count Number of shader objects to create.
 * @param createInfo Configures each shader object using its zero-based index.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateShadersEXT.html">vkCreateShadersEXT Manual Page</a>
 */
fun Device.createShaders(count: UInt, createInfo: VkShaderCreateInfoEXT.(UInt) -> Unit): List<Shader> = memScoped {
    assert(count > 0u) { "count must be greater than 0" }
    val createInfos = allocArray<VkShaderCreateInfoEXT>(count.toLong()) { index ->
        sType = VK_STRUCTURE_TYPE_SHADER_CREATE_INFO_EXT
        createInfo(index.toUInt())
    }
    val shaderHandles = allocArray<VkShaderEXTVar>(count.toLong())
    val result = vkCreateShadersEXT!!(handle, count, createInfos, null, shaderHandles)
    if (result != VK_SUCCESS) {
        repeat(count.toInt()) { index ->
            shaderHandles[index]?.let { vkDestroyShaderEXT!!(handle, it, null) }
        }
        result.checkResult("Failed to create shaders")
    }
    return (0 until count.toInt()).map { Shader(handle, shaderHandles[it]!!) }
}

/**
 * Write the implementation-defined shader binary into caller-provided memory without allocating a blob buffer.
 *
 * [destination] must remain valid and writable for the duration of the call, and its address must be aligned
 * to 16 bytes. Use [getBinaryDataSize] to query the capacity needed for the complete binary.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @param destination The memory region receiving the shader binary.
 * @return The number of bytes written and the Vulkan status. [VK_INCOMPLETE] indicates that the region was
 * too small for the complete binary; no data is written and the returned byte count is zero.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetShaderBinaryDataEXT.html">vkGetShaderBinaryDataEXT Manual Page</a>
 */
fun Shader.getBinaryData(destination: MemoryRegion): VulkanResult<ULong> = memScoped {
    assert(destination.address.rawValue.toLong() % 16L == 0L) { "destination address must be aligned to 16 bytes" }

    val dataSize = alloc<ULongVar> { value = destination.size }
    val result = vkGetShaderBinaryDataEXT!!(device, handle, dataSize.ptr, destination.address)
    if (result == VK_INCOMPLETE) {
        return VulkanResult(0uL, result)
    }
    result.checkResult("Failed to get shader binary data")
    return VulkanResult(dataSize.value, result)
}

/**
 * Get the size in bytes of this shader object's implementation-defined binary data.
 *
 * The binary data and its size remain unchanged for the lifetime of this shader object.
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetShaderBinaryDataEXT.html">vkGetShaderBinaryDataEXT Manual Page</a>
 */
fun Shader.getBinaryDataSize(): ULong = memScoped {
    val dataSize = alloc<ULongVar>()
    vkGetShaderBinaryDataEXT!!(device, handle, dataSize.ptr, null)
        .checkResult("Failed to get shader binary data size")

    return dataSize.value
}

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
 * Enable or disable depth clamping dynamically.
 *
 * Requires the `VK_EXT_shader_object` or `VK_EXT_extended_dynamic_state3` extension and corresponding feature.
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
 * Set the line rasterization mode dynamically.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature, or
 * `VK_EXT_extended_dynamic_state3` and its `extendedDynamicState3LineRasterizationMode` feature.
 * Line rasterization is provided by the required Vulkan 1.4 core API.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLineRasterizationModeEXT.html">vkCmdSetLineRasterizationModeEXT Manual Page</a>
 */
fun CommandBuffer.setLineRasterizationMode(mode: VkLineRasterizationModeEXT) {
    vkCmdSetLineRasterizationModeEXT!!(handle, mode)
}

/**
 * Enable or disable line stippling dynamically.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature, or
 * `VK_EXT_extended_dynamic_state3` and its `extendedDynamicState3LineStippleEnable` feature.
 * Line rasterization is provided by the required Vulkan 1.4 core API.
 * Configure the repeat factor and bit pattern with [CommandBuffer.setLineStipple].
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
