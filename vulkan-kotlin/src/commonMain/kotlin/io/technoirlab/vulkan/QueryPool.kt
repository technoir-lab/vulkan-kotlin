package io.technoirlab.vulkan

import io.technoirlab.volk.VK_OBJECT_TYPE_QUERY_POOL
import io.technoirlab.volk.VK_QUERY_RESULT_64_BIT
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkQueryPool
import io.technoirlab.volk.VkQueryResultFlags
import io.technoirlab.volk.vkDestroyQueryPool
import io.technoirlab.volk.vkGetQueryPoolResults
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.usePinned

/**
 * Wrapper for [VkQueryPool].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkQueryPool.html">VkQueryPool Manual Page</a>
 */
class QueryPool internal constructor(
    private val device: VkDevice,
    override val handle: VkQueryPool
) : Object<VkQueryPool> {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_QUERY_POOL

    /**
     * Retrieve status and results for a set of queries.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetQueryPoolResults.html">vkGetQueryPoolResults Manual Page</a>
     */
    fun getResults(firstQuery: UInt, queryCount: UInt, flags: VkQueryResultFlags = 0u): VulkanResult<ULongArray> {
        val resultArray = ULongArray(queryCount.toInt())
        val result = resultArray.usePinned { pinned ->
            vkGetQueryPoolResults!!(
                device,
                handle,
                firstQuery,
                queryCount,
                resultArray.size.toULong() * 8uL,
                pinned.addressOf(0),
                8uL,
                flags or VK_QUERY_RESULT_64_BIT
            )
        }
        return VulkanResult(resultArray, result)
    }

    /**
     * Destroy the query pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyQueryPool.html">vkDestroyQueryPool Manual Page</a>
     */
    override fun close() {
        vkDestroyQueryPool!!(device, handle, null)
    }
}
