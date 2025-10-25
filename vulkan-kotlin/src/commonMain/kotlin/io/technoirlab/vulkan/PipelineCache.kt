package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_PIPELINE_CACHE
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPipelineCache
import io.technoirlab.volk.vkDestroyPipelineCache
import io.technoirlab.volk.vkGetPipelineCacheData
import io.technoirlab.volk.vkMergePipelineCaches
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value

/**
 * Wrapper for [VkPipelineCache].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineCache.html">VkPipelineCache Manual Page</a>
 */
class PipelineCache internal constructor(
    private val device: VkDevice,
    override val handle: VkPipelineCache
) : Object<VkPipelineCache> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_PIPELINE_CACHE

    /**
     * Get the data from the pipeline cache.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPipelineCacheData.html">vkGetPipelineCacheData Manual Page</a>
     */
    context(allocator: MemScope)
    fun getData(): ByteArray {
        val dataSize = allocator.alloc<ULongVar>()
        vkGetPipelineCacheData!!(device, handle, dataSize.ptr, null)
            .checkResult("Failed to get pipeline cache data size")

        while (true) {
            val size = dataSize.value
            if (size == 0uL) return ByteArray(0)

            val buffer = ByteArray(size.toInt())
            buffer.usePinned {
                vkGetPipelineCacheData!!(device, handle, dataSize.ptr, it.addressOf(0))
                    .checkResult("Failed to get pipeline cache data")
            }

            if (dataSize.value == size) {
                return buffer
            }

            // Retry with updated size
        }
    }

    /**
     * Combine the data stores of pipeline caches.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkMergePipelineCaches.html">vkMergePipelineCaches Manual Page</a>
     */
    context(allocator: NativePlacement)
    fun merge(srcCaches: List<PipelineCache>) {
        val srcCacheHandles = allocator.allocArrayOf(srcCaches.map { it.handle })
        vkMergePipelineCaches!!(device, handle, srcCaches.size.toUInt(), srcCacheHandles)
            .checkResult("Failed to merge pipeline caches")
    }

    /**
     * Destroy the pipeline cache.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipelineCache.html">vkDestroyPipelineCache Manual Page</a>
     */
    override fun close() {
        vkDestroyPipelineCache!!(device, handle, null)
    }
}
