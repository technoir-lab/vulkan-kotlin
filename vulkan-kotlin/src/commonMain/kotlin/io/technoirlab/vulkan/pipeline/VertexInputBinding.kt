package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VkVertexInputBindingDescription2EXT
import io.technoirlab.volk.VkVertexInputRate

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

internal inline fun VkVertexInputBindingDescription2EXT.from(binding: VertexInputBinding) {
    this.binding = binding.binding
    stride = binding.stride
    inputRate = binding.inputRate
    divisor = binding.divisor
}
