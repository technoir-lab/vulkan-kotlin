package io.technoirlab.vulkan.device

import io.technoirlab.volk.VolkDeviceTable
import io.technoirlab.vulkan.fakeInstance
import io.technoirlab.vulkan.internal.DispatchTable
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf

/**
 * A device wrapper over a placeholder handle and a dispatch table that was never loaded. It reaches no Vulkan
 * implementation and must not be closed.
 */
internal fun NativePlacement.fakeDevice(): Device {
    val dispatchTable = DispatchTable(nativeHeap.alloc<VolkDeviceTable>(), sizeOf<VolkDeviceTable>())
    return Device(fakeInstance(), alloc<ByteVar>().ptr.reinterpret(), enabledExtensions = emptySet(), dispatchTable)
}
