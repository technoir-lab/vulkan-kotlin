package io.technoirlab.vulkan

import io.technoirlab.volk.VK_API_VERSION_1_3

/**
 * Specifies application information.
 */
data class ApplicationInfo(
    /**
     * The highest version of Vulkan that the application is designed to use.
     * The patch version number specified in [apiVersion] is ignored when creating an instance object.
     * The variant version of the instance must match that requested in [apiVersion].
     */
    val apiVersion: UInt = VK_API_VERSION_1_3,
    /**
     * The name of the application.
     */
    val applicationName: String? = null,
    /**
     * The developer-supplied version number of the application.
     */
    val applicationVersion: UInt = 0u,
    /**
     * The name of the engine (if any) used to create the application.
     */
    val engineName: String? = null,
    /**
     * The developer-supplied version number of the engine used to create the application.
     */
    val engineVersion: UInt = 0u,
)
