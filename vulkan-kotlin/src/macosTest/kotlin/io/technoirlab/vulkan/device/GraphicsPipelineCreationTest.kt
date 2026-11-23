package io.technoirlab.vulkan.device

import io.technoirlab.volk.VK_COLOR_COMPONENT_A_BIT
import io.technoirlab.volk.VK_COLOR_COMPONENT_B_BIT
import io.technoirlab.volk.VK_COLOR_COMPONENT_G_BIT
import io.technoirlab.volk.VK_COLOR_COMPONENT_R_BIT
import io.technoirlab.volk.VK_DYNAMIC_STATE_SCISSOR
import io.technoirlab.volk.VK_DYNAMIC_STATE_VIEWPORT
import io.technoirlab.volk.VK_FORMAT_R8G8B8A8_UNORM
import io.technoirlab.volk.VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT
import io.technoirlab.volk.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST
import io.technoirlab.volk.VK_SAMPLE_COUNT_1_BIT
import io.technoirlab.volk.VK_SHADER_STAGE_FRAGMENT_BIT
import io.technoirlab.volk.VK_SHADER_STAGE_VERTEX_BIT
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkPipelineColorBlendAttachmentState
import io.technoirlab.volk.VkPipelineColorBlendStateCreateInfo
import io.technoirlab.volk.VkPipelineCreateFlags2
import io.technoirlab.volk.VkPipelineDynamicStateCreateInfo
import io.technoirlab.volk.VkPipelineInputAssemblyStateCreateInfo
import io.technoirlab.volk.VkPipelineMultisampleStateCreateInfo
import io.technoirlab.volk.VkPipelineRasterizationStateCreateInfo
import io.technoirlab.volk.VkPipelineRenderingCreateInfo
import io.technoirlab.volk.VkPipelineShaderStageCreateInfo
import io.technoirlab.volk.VkPipelineViewportStateCreateInfo
import io.technoirlab.vulkan.Vulkan
import io.technoirlab.vulkan.pipeline.Pipeline
import io.technoirlab.vulkan.pipeline.PipelineCreationResult
import io.technoirlab.vulkan.pipeline.allocPipelineCreationFeedbackCreateInfo
import io.technoirlab.vulkan.withTestDevice
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cstr
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class GraphicsPipelineCreationTest {
    private val vulkan = Vulkan()

    @AfterTest
    fun tearDown() {
        vulkan.close()
    }

    @Test
    fun `createGraphicsPipeline creates a pipeline`() = withPipelineFixture { fixture ->
        val pipeline = fixture.createPipeline()

        assertNotNull(pipeline).close()
    }

    @Test
    fun `createGraphicsPipeline accepts failing on a required compile`() = withPipelineFixture { fixture ->
        val pipeline = fixture.createPipeline(VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT)

        pipeline?.close()
    }

    @Test
    fun `createGraphicsPipelineWithFeedback reports feedback for every stage`() = withPipelineFixture { fixture ->
        val result = fixture.createPipelineWithFeedback()

        assertNotNull(result.pipeline).close()
        assertEquals(STAGE_COUNT.toInt(), result.pipelineStageCreationFeedbacks.size)
    }

    @Test
    fun `createGraphicsPipelineWithFeedback reports feedback when failing on a required compile`() = withPipelineFixture { fixture ->
        val result = fixture.createPipelineWithFeedback(flags = VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT)

        result.pipeline?.close()
        assertEquals(STAGE_COUNT.toInt(), result.pipelineStageCreationFeedbacks.size)
    }

    @Test
    fun `createGraphicsPipelineWithFeedback rejects a feedback structure chained by the caller`() = withPipelineFixture { fixture ->
        val callerFeedback = allocPipelineCreationFeedbackCreateInfo(STAGE_COUNT)

        assertFailsWith<AssertionError> { fixture.createPipelineWithFeedback(callerFeedback.ptr) }
    }

    private fun withPipelineFixture(block: MemScope.(GraphicsPipelineFixture) -> Unit) {
        vulkan.withTestDevice(features13 = { pipelineCreationCacheControl = VK_TRUE }) { _, device ->
            memScoped {
                GraphicsPipelineFixture(device, scope = this).use { block(it) }
            }
        }
    }
}

private const val STAGE_COUNT = 2u

/**
 * The inputs of a minimal dynamic-rendering pipeline: a vertex and a fragment stage writing one color attachment.
 *
 * Native memory referenced by the pipeline state is allocated in [scope], which must outlive the fixture.
 */
private class GraphicsPipelineFixture(
    private val device: Device,
    scope: MemScope,
) : AutoCloseable {
    private val entryPoint = "main".cstr.getPointer(scope)
    private val colorFormat = scope.alloc<UIntVar> { value = VK_FORMAT_R8G8B8A8_UNORM }
    private val colorBlendAttachment = scope.alloc<VkPipelineColorBlendAttachmentState> {
        colorWriteMask = VK_COLOR_COMPONENT_R_BIT or VK_COLOR_COMPONENT_G_BIT or VK_COLOR_COMPONENT_B_BIT or VK_COLOR_COMPONENT_A_BIT
    }
    private val dynamicStates = scope.allocArray<UIntVar>(DYNAMIC_STATES.size) { value = DYNAMIC_STATES[it] }
    private val layout = device.createPipelineLayout()
    private val vertexShader = device.createShaderModule(VERTEX_SHADER)
    private val fragmentShader = device.createShaderModule(FRAGMENT_SHADER)

    private val stages: VkPipelineShaderStageCreateInfo.(UInt) -> Unit = { index ->
        stage = if (index == 0u) VK_SHADER_STAGE_VERTEX_BIT else VK_SHADER_STAGE_FRAGMENT_BIT
        module = (if (index == 0u) vertexShader else fragmentShader).handle
        pName = entryPoint
    }
    private val inputAssemblyState: VkPipelineInputAssemblyStateCreateInfo.() -> Unit = {
        topology = VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST
    }
    private val viewportState: VkPipelineViewportStateCreateInfo.() -> Unit = {
        viewportCount = 1u
        scissorCount = 1u
    }
    private val rasterizationState: VkPipelineRasterizationStateCreateInfo.() -> Unit = {
        lineWidth = 1f
    }
    private val multisampleState: VkPipelineMultisampleStateCreateInfo.() -> Unit = {
        rasterizationSamples = VK_SAMPLE_COUNT_1_BIT
    }
    private val colorBlendState: VkPipelineColorBlendStateCreateInfo.() -> Unit = {
        attachmentCount = 1u
        pAttachments = colorBlendAttachment.ptr
    }
    private val dynamicState: VkPipelineDynamicStateCreateInfo.() -> Unit = {
        dynamicStateCount = DYNAMIC_STATES.size.toUInt()
        pDynamicStates = dynamicStates
    }

    fun createPipeline(flags: VkPipelineCreateFlags2 = 0uL): Pipeline? = device.createGraphicsPipeline(
        layout,
        STAGE_COUNT,
        stages,
        inputAssemblyState = inputAssemblyState,
        viewportState = viewportState,
        rasterizationState = rasterizationState,
        multisampleState = multisampleState,
        colorBlendState = colorBlendState,
        dynamicState = dynamicState,
        renderingCreateInfo = renderingCreateInfo(extensions = null),
        flags = flags,
    )

    fun createPipelineWithFeedback(extensions: COpaquePointer? = null, flags: VkPipelineCreateFlags2 = 0uL): PipelineCreationResult =
        device.createGraphicsPipelineWithFeedback(
            layout,
            STAGE_COUNT,
            stages,
            inputAssemblyState = inputAssemblyState,
            viewportState = viewportState,
            rasterizationState = rasterizationState,
            multisampleState = multisampleState,
            colorBlendState = colorBlendState,
            dynamicState = dynamicState,
            renderingCreateInfo = renderingCreateInfo(extensions),
            flags = flags,
        )

    override fun close() {
        fragmentShader.close()
        vertexShader.close()
        layout.close()
    }

    private fun renderingCreateInfo(extensions: COpaquePointer?): VkPipelineRenderingCreateInfo.() -> Unit = {
        pNext = extensions
        colorAttachmentCount = 1u
        pColorAttachmentFormats = colorFormat.ptr
    }

    private companion object {
        private val DYNAMIC_STATES = listOf(VK_DYNAMIC_STATE_VIEWPORT, VK_DYNAMIC_STATE_SCISSOR)

        // Assembled with `spirv-as --target-env vulkan1.0` from:
        //                OpCapability Shader
        //                OpMemoryModel Logical GLSL450
        //                OpEntryPoint Vertex %main "main" %position
        //                OpDecorate %position BuiltIn Position
        //        %void = OpTypeVoid
        //    %function = OpTypeFunction %void
        //       %float = OpTypeFloat 32
        //        %vec4 = OpTypeVector %float 4
        //      %output = OpTypePointer Output %vec4
        //    %position = OpVariable %output Output
        //        %zero = OpConstant %float 0
        //         %one = OpConstant %float 1
        //      %origin = OpConstantComposite %vec4 %zero %zero %zero %one
        //        %main = OpFunction %void None %function
        //       %entry = OpLabel
        //                OpStore %position %origin
        //                OpReturn
        //                OpFunctionEnd
        private val VERTEX_SHADER = listOf(
            0x07230203u, 0x00010000u, 0x00070000u, 0x0000000cu, 0x00000000u, 0x00020011u, 0x00000001u, 0x0003000eu,
            0x00000000u, 0x00000001u, 0x0006000fu, 0x00000000u, 0x00000001u, 0x6e69616du, 0x00000000u, 0x00000002u,
            0x00040047u, 0x00000002u, 0x0000000bu, 0x00000000u, 0x00020013u, 0x00000003u, 0x00030021u, 0x00000004u,
            0x00000003u, 0x00030016u, 0x00000005u, 0x00000020u, 0x00040017u, 0x00000006u, 0x00000005u, 0x00000004u,
            0x00040020u, 0x00000007u, 0x00000003u, 0x00000006u, 0x0004003bu, 0x00000007u, 0x00000002u, 0x00000003u,
            0x0004002bu, 0x00000005u, 0x00000008u, 0x00000000u, 0x0004002bu, 0x00000005u, 0x00000009u, 0x3f800000u,
            0x0007002cu, 0x00000006u, 0x0000000au, 0x00000008u, 0x00000008u, 0x00000008u, 0x00000009u, 0x00050036u,
            0x00000003u, 0x00000001u, 0x00000000u, 0x00000004u, 0x000200f8u, 0x0000000bu, 0x0003003eu, 0x00000002u,
            0x0000000au, 0x000100fdu, 0x00010038u,
        )

        // Assembled with `spirv-as --target-env vulkan1.0` from:
        //                OpCapability Shader
        //                OpMemoryModel Logical GLSL450
        //                OpEntryPoint Fragment %main "main" %color
        //                OpExecutionMode %main OriginUpperLeft
        //                OpDecorate %color Location 0
        //        %void = OpTypeVoid
        //    %function = OpTypeFunction %void
        //       %float = OpTypeFloat 32
        //        %vec4 = OpTypeVector %float 4
        //      %output = OpTypePointer Output %vec4
        //       %color = OpVariable %output Output
        //         %one = OpConstant %float 1
        //       %white = OpConstantComposite %vec4 %one %one %one %one
        //        %main = OpFunction %void None %function
        //       %entry = OpLabel
        //                OpStore %color %white
        //                OpReturn
        //                OpFunctionEnd
        private val FRAGMENT_SHADER = listOf(
            0x07230203u, 0x00010000u, 0x00070000u, 0x0000000bu, 0x00000000u, 0x00020011u, 0x00000001u, 0x0003000eu,
            0x00000000u, 0x00000001u, 0x0006000fu, 0x00000004u, 0x00000001u, 0x6e69616du, 0x00000000u, 0x00000002u,
            0x00030010u, 0x00000001u, 0x00000007u, 0x00040047u, 0x00000002u, 0x0000001eu, 0x00000000u, 0x00020013u,
            0x00000003u, 0x00030021u, 0x00000004u, 0x00000003u, 0x00030016u, 0x00000005u, 0x00000020u, 0x00040017u,
            0x00000006u, 0x00000005u, 0x00000004u, 0x00040020u, 0x00000007u, 0x00000003u, 0x00000006u, 0x0004003bu,
            0x00000007u, 0x00000002u, 0x00000003u, 0x0004002bu, 0x00000005u, 0x00000008u, 0x3f800000u, 0x0007002cu,
            0x00000006u, 0x00000009u, 0x00000008u, 0x00000008u, 0x00000008u, 0x00000008u, 0x00050036u, 0x00000003u,
            0x00000001u, 0x00000000u, 0x00000004u, 0x000200f8u, 0x0000000au, 0x0003003eu, 0x00000002u, 0x00000009u,
            0x000100fdu, 0x00010038u,
        )
    }
}
