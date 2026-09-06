package io.technoirlab.vulkan

/**
 * Properties of an available Vulkan layer.
 *
 * @property name The name of the layer.
 * @property specVersion The Vulkan API version supported by the layer.
 * @property implementationVersion The implementation-specific version of the layer.
 * @property description The description of the layer.
 */
data class LayerProperties(
    val name: String,
    val specVersion: UInt,
    val implementationVersion: UInt,
    val description: String,
)
