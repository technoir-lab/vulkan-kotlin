package io.technoirlab.vulkan.shader

import io.technoirlab.volk.VK_INCOMPATIBLE_SHADER_BINARY_EXT
import io.technoirlab.volk.VK_INCOMPLETE
import io.technoirlab.volk.VK_OBJECT_TYPE_SHADER_EXT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SHADER_CREATE_INFO_EXT
import io.technoirlab.volk.VK_SUCCESS
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkResult
import io.technoirlab.volk.VkShaderCreateInfoEXT
import io.technoirlab.volk.VkShaderEXT
import io.technoirlab.volk.VkShaderEXTVar
import io.technoirlab.volk.VkShaderStageFlagBits
import io.technoirlab.volk.vkCmdBindShadersEXT
import io.technoirlab.volk.vkCreateShadersEXT
import io.technoirlab.volk.vkDestroyShaderEXT
import io.technoirlab.volk.vkGetShaderBinaryDataEXT
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.VulkanResult
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.command.CommandBuffer
import io.technoirlab.vulkan.device.Device
import io.technoirlab.vulkan.memory.MemoryRegion
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.get
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.assert

/**
 * Wrapper for [VkShaderEXT].
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkShaderEXT.html">VkShaderEXT Manual Page</a>
 */
class Shader internal constructor(
    internal val device: VkDevice,
    override val handle: VkShaderEXT,
) : VulkanObject,
    AutoCloseable {
    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_SHADER_EXT

    /**
     * Destroy the shader object.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyShaderEXT.html">vkDestroyShaderEXT Manual Page</a>
     */
    override fun close() {
        vkDestroyShaderEXT!!(device, handle, null)
    }
}

/**
 * Bind shader objects to stages in the command buffer.
 *
 * A `null` shader unbinds the corresponding stage. If [shaders] itself is `null`, all specified stages are unbound.
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @param stages Shader stages affected by the binding operation.
 * @param shaders Shader objects to bind, or `null` to unbind every specified stage.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindShadersEXT.html">vkCmdBindShadersEXT Manual Page</a>
 */
fun CommandBuffer.bindShaders(stages: List<VkShaderStageFlagBits>, shaders: List<Shader?>? = null): Unit = memScoped {
    assert(stages.isNotEmpty()) { "stages must not be empty" }
    assert(shaders == null || stages.size == shaders.size) { "stages and shaders must have the same size" }

    val stageValues = allocArray<UIntVar>(stages.size) { value = stages[it] }
    val shaderValues = shaders?.let { shaderList ->
        allocArray<VkShaderEXTVar>(shaderList.size) { index -> value = shaderList[index]?.handle }
    }
    vkCmdBindShadersEXT!!(handle, stages.size.toUInt(), stageValues, shaderValues)
}

/**
 * Create one or more shader objects.
 *
 * Any shaders created before a batch creation failure are destroyed before the failure is reported.
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * Binary shader code is guaranteed to be usable only on a device that reports the same `shaderBinaryUUID` and the
 * same or a higher `shaderBinaryVersion` as the device it was retrieved from. For any other binary the
 * implementation decides whether it is usable.
 *
 * @param count Number of shader objects to create.
 * @param createInfo Configures each shader object using its zero-based index.
 * @return The created shader objects in [createInfo] order, or `null` if the implementation reported
 * `VK_INCOMPATIBLE_SHADER_BINARY_EXT` because binary shader code supplied through [createInfo] is not compatible
 * with the device. No shader object of the batch is kept in that case, so create the shaders from SPIR-V instead.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateShadersEXT.html">vkCreateShadersEXT Manual Page</a>
 */
fun Device.createShaders(count: UInt, createInfo: VkShaderCreateInfoEXT.(UInt) -> Unit): List<Shader>? = memScoped {
    assert(count > 0u) { "count must be greater than 0" }
    val createInfos = allocArray<VkShaderCreateInfoEXT>(count.toLong()) { index ->
        sType = VK_STRUCTURE_TYPE_SHADER_CREATE_INFO_EXT
        createInfo(index.toUInt())
    }
    val shaderHandles = allocArray<VkShaderEXTVar>(count.toLong())
    val result = vkCreateShadersEXT!!(handle, count, createInfos, null, shaderHandles)
    if (result != VK_SUCCESS) {
        // Whatever the result, every element is either null or a valid handle that nothing else will release.
        repeat(count.toInt()) { index ->
            shaderHandles[index]?.let { vkDestroyShaderEXT!!(handle, it, null) }
        }
    }
    return result.toShadersOrNull(handle, List(count.toInt()) { shaderHandles[it] }, "Failed to create shaders")
}

/**
 * Write the implementation-defined shader binary into caller-provided memory without allocating a blob buffer.
 *
 * [destination] must remain valid and writable for the duration of the call, and its address must be aligned
 * to 16 bytes. Use [getBinaryDataSize] to query the capacity needed for the complete binary.
 *
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @param destination The memory region receiving the shader binary.
 * @return The number of bytes written and the Vulkan status. [VK_INCOMPLETE] indicates that the region was
 * too small for the complete binary; no data is written and the returned byte count is zero.
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetShaderBinaryDataEXT.html">vkGetShaderBinaryDataEXT Manual Page</a>
 */
fun Shader.getBinaryData(destination: MemoryRegion): VulkanResult<ULong> = memScoped {
    assert(destination.address.rawValue.toLong() % 16L == 0L) { "destination address must be aligned to 16 bytes" }

    val dataSize = alloc<ULongVar> { value = destination.size }
    val result = vkGetShaderBinaryDataEXT!!(device, handle, dataSize.ptr, destination.address)
    if (result == VK_INCOMPLETE) {
        return VulkanResult(0uL, result)
    }
    result.checkResult("Failed to get shader binary data")
    return VulkanResult(dataSize.value, result)
}

/**
 * Get the size in bytes of this shader object's implementation-defined binary data.
 *
 * The binary data and its size remain unchanged for the lifetime of this shader object.
 * Requires the `VK_EXT_shader_object` extension and its `shaderObject` feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetShaderBinaryDataEXT.html">vkGetShaderBinaryDataEXT Manual Page</a>
 */
fun Shader.getBinaryDataSize(): ULong = memScoped {
    val dataSize = alloc<ULongVar>()
    vkGetShaderBinaryDataEXT!!(device, handle, dataSize.ptr, null)
        .checkResult("Failed to get shader binary data size")

    return dataSize.value
}

/**
 * Interprets the result of a command that creates a batch of shader objects.
 *
 * `VK_INCOMPATIBLE_SHADER_BINARY_EXT` is a success code reporting that supplied binary shader code is not compatible
 * with the device, so it is not a failure. The caller releases the handles of a batch that is not returned.
 *
 * @param device The device the shader objects were created on.
 * @param shaders The handles the command wrote, all of which are non-null when the command returned `VK_SUCCESS`.
 * @param message Describes the failure when this result is neither `VK_SUCCESS` nor
 * `VK_INCOMPATIBLE_SHADER_BINARY_EXT`.
 * @return The created shader objects in [shaders] order, or `null` for `VK_INCOMPATIBLE_SHADER_BINARY_EXT`.
 */
internal fun VkResult.toShadersOrNull(device: VkDevice, shaders: List<VkShaderEXT?>, message: String): List<Shader>? {
    if (this == VK_INCOMPATIBLE_SHADER_BINARY_EXT) return null
    checkResult(message)
    return shaders.map { Shader(device, it!!) }
}
