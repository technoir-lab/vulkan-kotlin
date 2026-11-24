package io.technoirlab.vulkan.device

import io.technoirlab.vulkan.shader.ShaderModule
import io.technoirlab.vulkan.shader.allocSpirvCode
import kotlinx.cinterop.memScoped

// A compute shader that does nothing, as SPIR-V words. Assembled with `spirv-as --target-env vulkan1.0` from:
//                OpCapability Shader
//                OpMemoryModel Logical GLSL450
//                OpEntryPoint GLCompute %main "main"
//                OpExecutionMode %main LocalSize 1 1 1
//        %void = OpTypeVoid
//    %function = OpTypeFunction %void
//        %main = OpFunction %void None %function
//       %entry = OpLabel
//                OpReturn
//                OpFunctionEnd
internal val NOOP_COMPUTE_SHADER = listOf(
    0x07230203u, 0x00010000u, 0x00070000u, 0x00000005u, 0x00000000u, 0x00020011u, 0x00000001u, 0x0003000eu,
    0x00000000u, 0x00000001u, 0x0005000fu, 0x00000005u, 0x00000001u, 0x6e69616du, 0x00000000u, 0x00060010u,
    0x00000001u, 0x00000011u, 0x00000001u, 0x00000001u, 0x00000001u, 0x00020013u, 0x00000002u, 0x00030021u,
    0x00000003u, 0x00000002u, 0x00050036u, 0x00000002u, 0x00000001u, 0x00000000u, 0x00000003u, 0x000200f8u,
    0x00000004u, 0x000100fdu, 0x00010038u,
)

/**
 * Creates a shader module from SPIR-V [code] given as words.
 */
internal fun Device.createShaderModule(code: List<UInt>): ShaderModule = memScoped {
    createShaderModule(allocSpirvCode(code))
}
