package io.technoirlab.volk

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.posix.getenv
import platform.posix.setenv

private const val ENV_ADD_DRIVER_FILES = "VK_ADD_DRIVER_FILES"
private const val ENV_ADD_LAYER_PATH = "VK_ADD_LAYER_PATH"

/**
 * Adds bundled drivers and layers to the loader's standard discovery paths.
 */
@OptIn(ExperimentalForeignApi::class)
actual fun volkKotlinInitialize() {
    val root = NSBundle.mainBundle.resourcePath
        ?.trimEnd('/')
        ?.takeIf { it.isNotEmpty() }
        ?: return
    val bundleRoots = directoryContents(root)
        .filter { it.endsWith(".bundle") }
        .map { bundleResourceRoot("$root/$it") }

    val icdManifests = bundleRoots.flatMap { manifestFiles("$it/vulkan/icd.d") }
    appendEnvironmentPaths(ENV_ADD_DRIVER_FILES, icdManifests)

    val layerDirectories = bundleRoots
        .map { "$it/vulkan/explicit_layer.d" }
        .filter { manifestFiles(it).isNotEmpty() }
    appendEnvironmentPaths(ENV_ADD_LAYER_PATH, layerDirectories)
}

@OptIn(ExperimentalForeignApi::class)
private fun appendEnvironmentPaths(name: String, paths: List<String>) {
    if (paths.isEmpty()) return
    val existingPaths = getenv(name)?.toKString()?.split(':').orEmpty()
    val combinedPaths = (existingPaths + paths).filter { it.isNotEmpty() }.distinct()
    setenv(name, combinedPaths.joinToString(":"), 1)
}

private fun bundleResourceRoot(bundleRoot: String): String {
    val resourceRoot = NSBundle.bundleWithPath(bundleRoot)?.resourcePath
    return resourceRoot?.takeIf { directoryContents(it).isNotEmpty() } ?: bundleRoot
}

@OptIn(ExperimentalForeignApi::class)
private fun directoryContents(path: String): List<String> = NSFileManager.defaultManager
    .contentsOfDirectoryAtPath(path, error = null)
    .orEmpty()
    .filterIsInstance<String>()
    .sorted()

private fun manifestFiles(directory: String): List<String> = directoryContents(directory)
    .filter { it.endsWith(".json") }
    .map { "$directory/$it" }
