package io.technoirlab.vulkan

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer

/**
 * Represents a Vulkan object.
 */
interface Object<NativeHandleType : CPointer<out CPointed>> : AutoCloseable {
    /**
     * The native handle to the Vulkan object.
     */
    val handle: NativeHandleType
}
