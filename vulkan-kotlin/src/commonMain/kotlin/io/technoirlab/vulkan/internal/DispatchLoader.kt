package io.technoirlab.vulkan.internal

import io.technoirlab.volk.VkInstance
import io.technoirlab.volk.VolkInstanceTable
import io.technoirlab.volk.volkLoadInstanceTable
import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.sizeOf
import kotlin.concurrent.AtomicInt

/**
 * Loads the dispatch tables of instances and devices and keeps track of the ones in use.
 *
 * volk resolves device-level commands through its process-wide `vkGetDeviceProcAddr` pointer, and loading an
 * instance table overwrites that pointer. Table loads are therefore serialized.
 */
internal class DispatchLoader {
    private val isBusy = AtomicInt(0)
    private val loadedTables = mutableSetOf<DispatchTable<*>>()

    /**
     * Resolves the instance-level commands of [instance] into a new table.
     */
    fun loadInstanceTable(instance: VkInstance): DispatchTable<VolkInstanceTable> {
        val table = DispatchTable(nativeHeap.alloc<VolkInstanceTable>(), sizeOf<VolkInstanceTable>())
        exclusively {
            volkLoadInstanceTable(table.commands.ptr, instance)
            loadedTables += table
        }
        return table
    }

    /**
     * Clears [table], so that its commands can no longer be called, and stops tracking it.
     */
    fun unload(table: DispatchTable<*>): Unit = exclusively {
        table.clear()
        loadedTables -= table
    }

    /**
     * Unloads every table that is still loaded. Their commands point into the Vulkan loader, so this must happen
     * before the loader itself is unloaded.
     */
    fun unloadAll(): Unit = exclusively {
        loadedTables.forEach { it.clear() }
        loadedTables.clear()
    }

    private inline fun exclusively(block: () -> Unit) {
        while (!isBusy.compareAndSet(0, 1)) {
            // Table loads are rare and short, so spinning costs less than a native mutex.
        }
        try {
            block()
        } finally {
            isBusy.value = 0
        }
    }
}
