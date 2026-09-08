package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VkFormat
import io.technoirlab.volk.VkVertexInputAttributeDescription2EXT

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

internal inline fun VkVertexInputAttributeDescription2EXT.from(attribute: VertexInputAttribute) {
    location = attribute.location
    binding = attribute.binding
    format = attribute.format
    offset = attribute.offset
}
