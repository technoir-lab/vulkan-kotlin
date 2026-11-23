package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT
import io.technoirlab.volk.VK_PIPELINE_CREATION_FEEDBACK_BASE_PIPELINE_ACCELERATION_BIT
import io.technoirlab.volk.VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT
import io.technoirlab.volk.VkPipelineCreationFeedback
import io.technoirlab.volk.VkPipelineCreationFeedbackFlags

/**
 * Valid feedback reported by the implementation about the creation of a pipeline or of one of its shader stages.
 *
 * An implementation is not required to report feedback. Feedback it did not mark as valid carries no other
 * information, so it is represented by `null` wherever this type is returned. That keeps it distinct from a
 * valid zero [duration] and from valid feedback that reports no cache hit.
 *
 * @property flags The raw feedback flags. `VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT` is always set.
 * @property duration The time spent creating the pipeline or the stage, in nanoseconds.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineCreationFeedback.html">VkPipelineCreationFeedback Manual Page</a>
 */
data class PipelineCreationFeedback internal constructor(
    val flags: VkPipelineCreationFeedbackFlags,
    val duration: ULong,
) {
    /**
     * Whether the implementation found a readily usable pipeline or stage in the pipeline cache supplied to the
     * creation call.
     *
     * `false` means only that no hit was reported. Caches internal to the implementation are not reported here,
     * and a short [duration] does not establish that one was used.
     *
     * A hit is reported separately for the pipeline and for each of its stages, and neither implies the other.
     * An implementation may report a hit for the whole pipeline and none for its stages, or hits for individual
     * stages and none for the pipeline. Check the pipeline feedback and every stage feedback to learn whether
     * the cache was used at all.
     */
    val applicationPipelineCacheHit: Boolean
        get() = flags and VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT != 0u

    /**
     * Whether the implementation used the base pipeline supplied to the creation call to accelerate it.
     *
     * `false` means only that no acceleration was reported.
     */
    val basePipelineAcceleration: Boolean
        get() = flags and VK_PIPELINE_CREATION_FEEDBACK_BASE_PIPELINE_ACCELERATION_BIT != 0u
}

/**
 * Copies the feedback into a Kotlin value, or returns `null` unless the implementation marked it as valid.
 *
 * Every other field is undefined without `VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT`, so none of them is read.
 */
internal inline fun VkPipelineCreationFeedback.toPipelineCreationFeedback(): PipelineCreationFeedback? =
    if (flags and VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT != 0u) PipelineCreationFeedback(flags, duration) else null
