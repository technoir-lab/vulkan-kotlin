package io.technoirlab.vulkan.internal

import kotlinx.cinterop.CStructVar
import kotlinx.cinterop.convert
import kotlinx.cinterop.free
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import platform.posix.memset
import kotlin.native.ref.createCleaner

/**
 * The commands resolved for one instance or device, together with the native memory that holds them.
 *
 * The memory is released when this object becomes unreachable, not when the table is cleared: wrappers that outlive
 * their instance or device still read the cleared table and fail. Code that keeps only [commands] does not keep the
 * memory alive.
 *
 * @property commands The table, which must be allocated from [nativeHeap].
 * @param size The size of [commands] in bytes.
 */
internal class DispatchTable<T : CStructVar>(
    val commands: T,
    private val size: Long,
) {
    private val cleaner = createCleaner(commands) { nativeHeap.free(it) }

    /**
     * Makes every command read as `null`, so that calling one fails instead of reaching an object that no longer
     * exists.
     */
    fun clear() {
        memset(commands.ptr, 0, size.convert())
    }
}
