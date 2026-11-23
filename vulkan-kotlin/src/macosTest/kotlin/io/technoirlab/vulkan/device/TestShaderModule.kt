package io.technoirlab.vulkan.device

import io.technoirlab.vulkan.shader.ShaderModule
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.value

/**
 * Creates a shader module from SPIR-V [code] given as words.
 */
internal fun Device.createShaderModule(code: List<UInt>): ShaderModule = memScoped {
    val words = allocArray<UIntVar>(code.size) { value = code[it] }
    createShaderModule {
        codeSize = (code.size * UInt.SIZE_BYTES).toULong()
        pCode = words
    }
}
