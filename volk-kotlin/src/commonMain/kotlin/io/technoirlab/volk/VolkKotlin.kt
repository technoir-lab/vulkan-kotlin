package io.technoirlab.volk

/**
 * Initialize Volk Kotlin.
 *
 * On Apple platforms this appends SwiftPM driver manifests to `VK_ADD_DRIVER_FILES` and bundled
 * layer directories to `VK_ADD_LAYER_PATH`, preserving existing entries and standard system discovery.
 * The loader applies its usual overrides: `VK_DRIVER_FILES` takes precedence over `VK_ICD_FILENAMES`,
 * and either overrides `VK_ADD_DRIVER_FILES`; `VK_LAYER_PATH` overrides `VK_ADD_LAYER_PATH`.
 * Override and driver-filter variables are left unchanged. Repeated calls do not duplicate entries.
 * It must be called before the Vulkan loader is initialized.
 * On other platforms it currently does nothing.
 */
expect fun volkKotlinInitialize()
