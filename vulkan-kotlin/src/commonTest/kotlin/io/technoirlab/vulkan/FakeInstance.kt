package io.technoirlab.vulkan

import io.technoirlab.volk.VolkInstanceTable
import io.technoirlab.vulkan.internal.DispatchLoader
import io.technoirlab.vulkan.internal.DispatchTable
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf

/**
 * An instance wrapper over a placeholder handle and a dispatch table that was never loaded. It reaches no Vulkan
 * implementation and must not be closed.
 */
internal fun NativePlacement.fakeInstance(): Instance {
    val dispatchTable = DispatchTable(nativeHeap.alloc<VolkInstanceTable>(), sizeOf<VolkInstanceTable>())
    return Instance(alloc<ByteVar>().ptr.reinterpret(), enabledExtensions = emptySet(), dispatchTable, DispatchLoader())
}
