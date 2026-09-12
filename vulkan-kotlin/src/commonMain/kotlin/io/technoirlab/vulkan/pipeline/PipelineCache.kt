package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_INCOMPLETE
import io.technoirlab.volk.VK_OBJECT_TYPE_PIPELINE_CACHE
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPipelineCache
import io.technoirlab.volk.vkDestroyPipelineCache
import io.technoirlab.volk.vkGetPipelineCacheData
import io.technoirlab.volk.vkMergePipelineCaches
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.VulkanResult
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.memory.MemoryRegion
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.assert

/**
 * Wrapper for [VkPipelineCache].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineCache.html">VkPipelineCache Manual Page</a>
 */
class PipelineCache internal constructor(
    private val device: VkDevice,
    override val handle: VkPipelineCache,
) : VulkanObject,
    AutoCloseable {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_PIPELINE_CACHE

    /**
     * Destroy the pipeline cache.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipelineCache.html">vkDestroyPipelineCache Manual Page</a>
     */
    override fun close() {
        vkDestroyPipelineCache!!(device, handle, null)
    }

    /**
     * Write pipeline cache data into caller-provided memory without allocating a blob buffer.
     *
     * [destination] must remain valid and writable for the duration of the call. At most [MemoryRegion.size]
     * bytes are written. Use [getDataSize] to query the capacity needed for a complete export.
     *
     * @param destination The memory region receiving the cache data.
     * @return The number of bytes written and the Vulkan status. [VK_INCOMPLETE] indicates that the region was
     * too small for the complete cache; any returned data is still valid cache data. The byte count is not the
     * required capacity. Query [getDataSize] again before retrying with a larger region.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPipelineCacheData.html">vkGetPipelineCacheData Manual Page</a>
     */
    fun getData(destination: MemoryRegion): VulkanResult<ULong> = memScoped {
        val dataSize = alloc<ULongVar> { value = destination.size }
        val result = vkGetPipelineCacheData!!(device, handle, dataSize.ptr, destination.address)
        if (result != VK_INCOMPLETE) {
            result.checkResult("Failed to get pipeline cache data")
        }
        return VulkanResult(dataSize.value, result)
    }

    /**
     * Get the maximum size in bytes of the data that can be retrieved from this pipeline cache.
     *
     * The required capacity may change if the cache is modified before [getData] is called.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPipelineCacheData.html">vkGetPipelineCacheData Manual Page</a>
     */
    fun getDataSize(): ULong = memScoped {
        val dataSize = alloc<ULongVar>()
        vkGetPipelineCacheData!!(device, handle, dataSize.ptr, null)
            .checkResult("Failed to get pipeline cache data size")
        return dataSize.value
    }

    /**
     * Combine the data stores of pipeline caches.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkMergePipelineCaches.html">vkMergePipelineCaches Manual Page</a>
     */
    fun merge(srcCaches: List<PipelineCache>): Unit = memScoped {
        assert(srcCaches.isNotEmpty()) { "srcCaches must not be empty" }

        val srcCacheHandles = allocArrayOf(srcCaches.map { it.handle })
        vkMergePipelineCaches!!(device, handle, srcCaches.size.toUInt(), srcCacheHandles)
            .checkResult("Failed to merge pipeline caches")
    }
}
