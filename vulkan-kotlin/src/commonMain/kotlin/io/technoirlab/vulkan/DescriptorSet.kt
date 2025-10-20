package io.technoirlab.vulkan

import io.technoirlab.volk.VkDescriptorSet

/**
 * Wrapper for [VkDescriptorSet].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDescriptorSet.html">VkDescriptorSet Manual Page</a>
 */
class DescriptorSet(
    override val handle: VkDescriptorSet
) : Object<VkDescriptorSet> {

    /**
     * No-op. Use [DescriptorPool.freeDescriptorSets] to free descriptor sets.
     */
    override fun close() = Unit
}
