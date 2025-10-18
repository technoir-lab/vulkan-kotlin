@file:OptIn(ExperimentalNativeApi::class)

package io.technoirlab.vulkan

import io.technoirlab.volk.VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
import io.technoirlab.volk.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
import io.technoirlab.volk.VkInstanceCreateInfo
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.toCStringArray
import kotlin.experimental.ExperimentalNativeApi

context(memScope: MemScope)
internal fun VkInstanceCreateInfo.withExtensions(vararg extensions: String) {
    val extensions = extensions.toMutableList()
    if (Platform.osFamily.isAppleFamily() && VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME !in extensions) {
        extensions += VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME
    }
    ppEnabledExtensionNames = extensions.toCStringArray(memScope)
    enabledExtensionCount = extensions.size.toUInt()

    if (VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME in extensions) {
        flags = flags or VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR
    }
}

private fun OsFamily.isAppleFamily(): Boolean =
    when (this) {
        OsFamily.MACOSX, OsFamily.IOS, OsFamily.TVOS, OsFamily.WATCHOS -> true
        else -> false
    }
