package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_SAMPLER
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkSampler
import io.technoirlab.volk.vkDestroySampler
import kotlinx.cinterop.invoke

/**
 * Wrapper for [VkSampler].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkSampler.html">VkSampler Manual Page</a>
 */
class Sampler(
    private val device: VkDevice,
    override val handle: VkSampler
) : Object<VkSampler> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_SAMPLER

    /**
     * Destroy the sampler.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroySampler.html">vkDestroySampler Manual Page</a>
     */
    override fun close() {
        vkDestroySampler!!(device, handle, null)
    }
}
