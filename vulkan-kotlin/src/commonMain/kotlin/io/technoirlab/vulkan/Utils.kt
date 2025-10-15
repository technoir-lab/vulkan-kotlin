@file:Suppress("NOTHING_TO_INLINE")

package io.technoirlab.vulkan

import io.technoirlab.volk.VK_FALSE
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkBool32

internal inline fun Boolean.toVkBool32(): VkBool32 =
    if (this) VK_TRUE else VK_FALSE

internal fun <T> nCopies(size: Int, element: T): List<T> = NCopiesList(size, element)

private class NCopiesList<T>(
    override val size: Int,
    private val element: T
) : AbstractList<T>(), RandomAccess {

    init {
        require(size >= 0) { "size must be non-negative" }
    }

    override fun get(index: Int): T {
        if (index !in 0 until size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
        return element
    }
}
