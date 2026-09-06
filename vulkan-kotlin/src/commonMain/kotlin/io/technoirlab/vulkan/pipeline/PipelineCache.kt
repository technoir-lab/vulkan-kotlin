package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_OBJECT_TYPE_PIPELINE_CACHE
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPipelineCache
import io.technoirlab.volk.vkDestroyPipelineCache
import io.technoirlab.volk.vkGetPipelineCacheData
import io.technoirlab.volk.vkMergePipelineCaches
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
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
     * Get the data from the pipeline cache.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetPipelineCacheData.html">vkGetPipelineCacheData Manual Page</a>
     */
    fun getData(): ByteArray = memScoped {
        val dataSize = alloc<ULongVar>()
        vkGetPipelineCacheData!!(device, handle, dataSize.ptr, null)
            .checkResult("Failed to get pipeline cache data size")

        var data: ByteArray
        while (true) {
            val size = dataSize.value
            if (size == 0uL) {
                data = ByteArray(0)
                break
            }

            val buffer = ByteArray(size.toInt())
            buffer.usePinned {
                vkGetPipelineCacheData!!(device, handle, dataSize.ptr, it.addressOf(0))
                    .checkResult("Failed to get pipeline cache data")
            }

            if (dataSize.value == size) {
                data = buffer
                break
            }

            // Retry with updated size
        }
        data
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

    /**
     * Destroy the pipeline cache.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyPipelineCache.html">vkDestroyPipelineCache Manual Page</a>
     */
    override fun close() {
        vkDestroyPipelineCache!!(device, handle, null)
    }
}
