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
 * A created pipeline together with the feedback the implementation reported about its creation.
 *
 * The caller owns [pipeline] and must close it. The feedback is copied out of native memory and stays usable
 * after the pipeline is closed.
 *
 * Each feedback is valid independently of the others. In particular, an implementation that reports an
 * application pipeline cache hit for the whole pipeline may report nothing for its stages.
 *
 * @property pipeline The created pipeline.
 * @property pipelineCreationFeedback Feedback about the creation of the whole pipeline, or `null` if the
 * implementation reported none.
 * @property pipelineStageCreationFeedbacks Feedback about each shader stage, in the order the stages were supplied
 * to the creation call. A compute pipeline has a single stage. An entry is `null` if the implementation reported
 * no feedback for that stage.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineCreationFeedbackCreateInfo.html">VkPipelineCreationFeedbackCreateInfo Manual Page</a>
 */
data class PipelineWithCreationFeedback internal constructor(
    val pipeline: Pipeline,
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
internal fun VkPipelineCreationFeedbackCreateInfo.toPipelineWithCreationFeedback(pipeline: Pipeline): PipelineWithCreationFeedback {
    val stageFeedbacks = pPipelineStageCreationFeedbacks
    return PipelineWithCreationFeedback(
        pipeline,
        pipelineCreationFeedback = pPipelineCreationFeedback!!.pointed.toPipelineCreationFeedback(),
        pipelineStageCreationFeedbacks = List(pipelineStageCreationFeedbackCount.toInt()) {
            stageFeedbacks!![it].toPipelineCreationFeedback()
        },
    )
}
