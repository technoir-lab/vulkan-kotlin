package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_OBJECT_TYPE_PIPELINE_LAYOUT
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPipelineLayout
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.device.Device
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkPipelineLayout].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineLayout.html">VkPipelineLayout Manual Page</a>
 */
class PipelineLayout internal constructor(
    private val device: Device,
    override val handle: VkPipelineLayout,
) : VulkanObject,
    AutoCloseable {
    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_PIPELINE_LAYOUT

    /**
     * Destroy the pipeline layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipelineLayout.html">vkDestroyPipelineLayout Manual Page</a>
     */
    override fun close() {
        device.dispatch.vkDestroyPipelineLayout!!(device.handle, handle, null)
    }
}
