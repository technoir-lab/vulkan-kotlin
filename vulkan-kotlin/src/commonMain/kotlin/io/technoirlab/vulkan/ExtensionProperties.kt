package io.technoirlab.vulkan

/**
 * Properties of a supported Vulkan extension.
 *
 * @property name The name of the extension.
 * @property specVersion The version of the extension specification implemented.
 */
data class ExtensionProperties internal constructor(
    val name: String,
    val specVersion: UInt,
)
