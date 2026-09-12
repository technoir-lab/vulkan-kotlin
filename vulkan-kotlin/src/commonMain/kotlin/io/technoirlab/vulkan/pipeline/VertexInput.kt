package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VkFormat
import io.technoirlab.volk.VkVertexInputAttributeDescription2EXT
import io.technoirlab.volk.VkVertexInputBindingDescription2EXT
import io.technoirlab.volk.VkVertexInputRate

/**
 * A vertex attribute read from a vertex buffer binding.
 *
 * @property location The shader input location.
 * @property binding The vertex buffer binding number.
 * @property format The attribute data format.
 * @property offset The byte offset of the attribute within a vertex.
 */
data class VertexInputAttribute(
    val location: UInt,
    val binding: UInt,
    val format: VkFormat,
    val offset: UInt,
)

/**
 * The layout and input rate of a vertex buffer binding.
 *
 * @property binding The vertex buffer binding number.
 * @property stride The byte distance between consecutive elements.
 * @property inputRate Whether attributes advance per vertex or per instance.
 * @property divisor The number of instances sharing one attribute value when using an instance input rate.
 */
data class VertexInputBinding(
    val binding: UInt,
    val stride: UInt,
    val inputRate: VkVertexInputRate,
    val divisor: UInt = 1u,
)

internal inline fun VkVertexInputAttributeDescription2EXT.from(attribute: VertexInputAttribute) {
    location = attribute.location
    binding = attribute.binding
    format = attribute.format
    offset = attribute.offset
}

internal inline fun VkVertexInputBindingDescription2EXT.from(binding: VertexInputBinding) {
    this.binding = binding.binding
    stride = binding.stride
    inputRate = binding.inputRate
    divisor = binding.divisor
}
