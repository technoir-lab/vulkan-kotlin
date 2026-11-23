package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_OBJECT_TYPE_PIPELINE
import io.technoirlab.volk.VK_PIPELINE_COMPILE_REQUIRED
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPipeline
import io.technoirlab.volk.VkResult
import io.technoirlab.volk.vkDestroyPipeline
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkPipeline].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipeline.html">VkPipeline Manual Page</a>
 */
class Pipeline internal constructor(
    private val device: VkDevice,
    override val handle: VkPipeline,
) : VulkanObject,
    AutoCloseable {
    override val type: VkObjectType get() = VK_OBJECT_TYPE_PIPELINE

    /**
     * Destroy the pipeline.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipeline.html">vkDestroyPipeline Manual Page</a>
     */
    override fun close() {
        vkDestroyPipeline!!(device, handle, null)
    }
}

/**
 * Interprets the result of a command that creates a single pipeline.
 *
 * `VK_PIPELINE_COMPILE_REQUIRED` is a success code reporting that the implementation created no pipeline because
 * it would have had to compile it, so it is not a failure.
 *
 * @param device The device the pipeline was created on.
 * @param pipeline The handle the command wrote, which is null unless the command returned `VK_SUCCESS`.
 * @param message Describes the failure when this result is neither `VK_SUCCESS` nor `VK_PIPELINE_COMPILE_REQUIRED`.
 * @return The created pipeline, or `null` for `VK_PIPELINE_COMPILE_REQUIRED`.
 */
internal fun VkResult.toPipelineOrNull(device: VkDevice, pipeline: VkPipeline?, message: String): Pipeline? {
    if (this == VK_PIPELINE_COMPILE_REQUIRED) return null
    checkResult(message)
    return Pipeline(device, pipeline!!)
}
