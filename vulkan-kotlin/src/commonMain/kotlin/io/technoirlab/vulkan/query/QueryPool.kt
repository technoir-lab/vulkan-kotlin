package io.technoirlab.vulkan.query

import io.technoirlab.volk.VK_NOT_READY
import io.technoirlab.volk.VK_OBJECT_TYPE_QUERY_POOL
import io.technoirlab.volk.VK_QUERY_RESULT_64_BIT
import io.technoirlab.volk.VK_QUERY_RESULT_WITH_AVAILABILITY_BIT
import io.technoirlab.volk.VK_QUERY_RESULT_WITH_STATUS_BIT_KHR
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkQueryPool
import io.technoirlab.volk.VkQueryResultFlags
import io.technoirlab.volk.vkDestroyQueryPool
import io.technoirlab.volk.vkGetQueryPoolResults
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.VulkanResult
import io.technoirlab.vulkan.checkResult
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.usePinned
import kotlin.assert

/**
 * Wrapper for [VkQueryPool].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkQueryPool.html">VkQueryPool Manual Page</a>
 */
class QueryPool internal constructor(
    private val device: VkDevice,
    override val handle: VkQueryPool,
    private val valuesPerQuery: Int = 1,
) : VulkanObject,
    AutoCloseable {
    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_QUERY_POOL

    /**
     * Retrieve status and results for a set of queries.
     *
     * Each query writes its 64-bit result values consecutively, followed by an availability or status value when
     * `VK_QUERY_RESULT_WITH_AVAILABILITY_BIT` or `VK_QUERY_RESULT_WITH_STATUS_BIT_KHR` is set in [flags].
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetQueryPoolResults.html">vkGetQueryPoolResults Manual Page</a>
     */
    fun getResults(firstQuery: UInt, queryCount: UInt, flags: VkQueryResultFlags = 0u): VulkanResult<ULongArray> {
        assert(queryCount > 0u) { "queryCount must be greater than 0" }
        val extraValues = if ((flags and (VK_QUERY_RESULT_WITH_AVAILABILITY_BIT or VK_QUERY_RESULT_WITH_STATUS_BIT_KHR)) != 0u) {
            1
        } else {
            0
        }
        val valuesPerEntry = valuesPerQuery + extraValues
        assert(valuesPerEntry > 0) { "Query must produce at least one result value" }
        val stride = valuesPerEntry.toULong() * ULong.SIZE_BYTES.toULong()
        val resultArray = ULongArray(queryCount.toInt() * valuesPerEntry)
        val result = resultArray.usePinned { pinned ->
            vkGetQueryPoolResults!!(
                device,
                handle,
                firstQuery,
                queryCount,
                resultArray.size.toULong() * ULong.SIZE_BYTES.toULong(),
                pinned.addressOf(0),
                stride,
                flags or VK_QUERY_RESULT_64_BIT,
            )
        }
        if (result != VK_NOT_READY) {
            result.checkResult("Failed to get query pool results")
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
