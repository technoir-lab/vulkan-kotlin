package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_CREATION_FEEDBACK_CREATE_INFO
import io.technoirlab.volk.VkPipelineCreationFeedback
import io.technoirlab.volk.VkPipelineCreationFeedbackCreateInfo
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr

/**
 * The outcome of a pipeline creation call together with the feedback the implementation reported about it.
 *
 * The caller owns [pipeline] and must close it. The feedback is copied out of native memory and stays usable
 * after the pipeline is closed. It is reported whether or not a pipeline was created.
 *
 * Each feedback is valid independently of the others, and [pipelineCreationFeedback] does not summarize
 * [pipelineStageCreationFeedbacks]. An implementation may report an application pipeline cache hit for the whole
 * pipeline and nothing for its stages, or hits for individual stages and none for the pipeline.
 *
 * @property pipeline The created pipeline, or `null` if the creation call reported `VK_PIPELINE_COMPILE_REQUIRED`.
 * That happens only when the pipeline creation flags include `VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT`
 * and the implementation would have had to compile the pipeline.
 * @property pipelineCreationFeedback Feedback about the creation of the whole pipeline, or `null` if the
 * implementation reported none.
 * @property pipelineStageCreationFeedbacks Feedback about each shader stage, in the order the stages were supplied
 * to the creation call. A compute pipeline has a single stage. An entry is `null` if the implementation reported
 * no feedback for that stage.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineCreationFeedbackCreateInfo.html">VkPipelineCreationFeedbackCreateInfo Manual Page</a>
 */
data class PipelineCreationResult internal constructor(
    val pipeline: Pipeline?,
    val pipelineCreationFeedback: PipelineCreationFeedback?,
    val pipelineStageCreationFeedbacks: List<PipelineCreationFeedback?>,
)

/**
 * Allocates a structure requesting creation feedback for a pipeline with [stageCount] stages, together with the
 * storage the implementation writes the feedback to.
 *
 * The storage is zero-filled, so feedback the implementation never writes reads as not valid.
 */
internal fun NativePlacement.allocPipelineCreationFeedbackCreateInfo(stageCount: UInt): VkPipelineCreationFeedbackCreateInfo = alloc {
    sType = VK_STRUCTURE_TYPE_PIPELINE_CREATION_FEEDBACK_CREATE_INFO
    pPipelineCreationFeedback = alloc<VkPipelineCreationFeedback>().ptr
    pipelineStageCreationFeedbackCount = stageCount
    pPipelineStageCreationFeedbacks = if (stageCount > 0u) allocArray(stageCount.toLong()) else null
}

/**
 * Copies the feedback written through this structure into Kotlin values and pairs it with [pipeline].
 */
internal fun VkPipelineCreationFeedbackCreateInfo.toPipelineCreationResult(pipeline: Pipeline?): PipelineCreationResult {
    val stageFeedbacks = pPipelineStageCreationFeedbacks
    return PipelineCreationResult(
        pipeline,
        pipelineCreationFeedback = pPipelineCreationFeedback!!.pointed.toPipelineCreationFeedback(),
        pipelineStageCreationFeedbacks = List(pipelineStageCreationFeedbackCount.toInt()) {
            stageFeedbacks!![it].toPipelineCreationFeedback()
        },
    )
}
