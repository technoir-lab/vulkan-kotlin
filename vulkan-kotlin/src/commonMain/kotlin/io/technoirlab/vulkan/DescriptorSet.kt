package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_DESCRIPTOR_SET
import io.technoirlab.volk.VkDescriptorSet
import io.technoirlab.volk.VkObjectType

/**
 * Wrapper for [VkDescriptorSet].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDescriptorSet.html">VkDescriptorSet Manual Page</a>
 */
class DescriptorSet(
    override val handle: VkDescriptorSet
) : Object<VkDescriptorSet> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_DESCRIPTOR_SET

    /**
     * No-op. Use [DescriptorPool.freeDescriptorSets] to free descriptor sets.
     */
    override fun close() = Unit
}
