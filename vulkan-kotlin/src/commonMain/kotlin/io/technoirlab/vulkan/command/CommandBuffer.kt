package io.technoirlab.vulkan.command

import io.technoirlab.volk.VK_ATTACHMENT_UNUSED
import io.technoirlab.volk.VK_FRAGMENT_SHADING_RATE_COMBINER_OP_KEEP_KHR
import io.technoirlab.volk.VK_INDEX_TYPE_UINT16
import io.technoirlab.volk.VK_INDEX_TYPE_UINT32
import io.technoirlab.volk.VK_INDEX_TYPE_UINT8
import io.technoirlab.volk.VK_OBJECT_TYPE_COMMAND_BUFFER
import io.technoirlab.volk.VK_PIPELINE_BIND_POINT_GRAPHICS
import io.technoirlab.volk.VK_PIPELINE_STAGE_2_HOST_BIT
import io.technoirlab.volk.VK_QUERY_RESULT_64_BIT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BLIT_IMAGE_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_BUFFER_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_BUFFER_TO_IMAGE_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_IMAGE_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COPY_IMAGE_TO_BUFFER_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEPENDENCY_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PUSH_DESCRIPTOR_SET_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_RENDERING_ATTACHMENT_LOCATION_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_RENDERING_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_RENDERING_INPUT_ATTACHMENT_INDEX_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_RESOLVE_IMAGE_INFO_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SAMPLE_LOCATIONS_INFO_EXT
import io.technoirlab.volk.VK_WHOLE_SIZE
import io.technoirlab.volk.VkBlitImageInfo2
import io.technoirlab.volk.VkBufferCopy2
import io.technoirlab.volk.VkBufferImageCopy2
import io.technoirlab.volk.VkBufferMemoryBarrier2
import io.technoirlab.volk.VkBufferVar
import io.technoirlab.volk.VkClearAttachment
import io.technoirlab.volk.VkClearColorValue
import io.technoirlab.volk.VkClearDepthStencilValue
import io.technoirlab.volk.VkClearRect
import io.technoirlab.volk.VkCommandBuffer
import io.technoirlab.volk.VkCommandBufferBeginInfo
import io.technoirlab.volk.VkCommandBufferResetFlags
import io.technoirlab.volk.VkCommandBufferUsageFlags
import io.technoirlab.volk.VkCompareOp
import io.technoirlab.volk.VkCopyBufferInfo2
import io.technoirlab.volk.VkCopyBufferToImageInfo2
import io.technoirlab.volk.VkCopyImageInfo2
import io.technoirlab.volk.VkCopyImageToBufferInfo2
import io.technoirlab.volk.VkCullModeFlags
import io.technoirlab.volk.VkDependencyFlags
import io.technoirlab.volk.VkDependencyInfo
import io.technoirlab.volk.VkExtent2D
import io.technoirlab.volk.VkFilter
import io.technoirlab.volk.VkFragmentShadingRateCombinerOpKHR
import io.technoirlab.volk.VkFragmentShadingRateCombinerOpKHRVar
import io.technoirlab.volk.VkFrontFace
import io.technoirlab.volk.VkImageBlit2
import io.technoirlab.volk.VkImageCopy2
import io.technoirlab.volk.VkImageLayout
import io.technoirlab.volk.VkImageMemoryBarrier2
import io.technoirlab.volk.VkImageResolve2
import io.technoirlab.volk.VkImageSubresourceRange
import io.technoirlab.volk.VkIndexType
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPipelineBindPoint
import io.technoirlab.volk.VkPipelineStageFlags2
import io.technoirlab.volk.VkPolygonMode
import io.technoirlab.volk.VkPrimitiveTopology
import io.technoirlab.volk.VkPushDescriptorSetInfo
import io.technoirlab.volk.VkQueryControlFlags
import io.technoirlab.volk.VkQueryResultFlags
import io.technoirlab.volk.VkRect2D
import io.technoirlab.volk.VkRenderingAttachmentLocationInfo
import io.technoirlab.volk.VkRenderingInfo
import io.technoirlab.volk.VkRenderingInputAttachmentIndexInfo
import io.technoirlab.volk.VkResolveImageInfo2
import io.technoirlab.volk.VkResolveImageModeInfoKHR
import io.technoirlab.volk.VkSampleCountFlagBits
import io.technoirlab.volk.VkSampleLocationEXT
import io.technoirlab.volk.VkSampleLocationsInfoEXT
import io.technoirlab.volk.VkShaderStageFlags
import io.technoirlab.volk.VkStencilFaceFlags
import io.technoirlab.volk.VkStencilOp
import io.technoirlab.volk.VkViewport
import io.technoirlab.volk.vkBeginCommandBuffer
import io.technoirlab.volk.vkCmdBeginQuery
import io.technoirlab.volk.vkCmdBeginRendering
import io.technoirlab.volk.vkCmdBindDescriptorSets
import io.technoirlab.volk.vkCmdBindIndexBuffer
import io.technoirlab.volk.vkCmdBindIndexBuffer2
import io.technoirlab.volk.vkCmdBindPipeline
import io.technoirlab.volk.vkCmdBindVertexBuffers2
import io.technoirlab.volk.vkCmdBlitImage2
import io.technoirlab.volk.vkCmdClearAttachments
import io.technoirlab.volk.vkCmdClearColorImage
import io.technoirlab.volk.vkCmdClearDepthStencilImage
import io.technoirlab.volk.vkCmdCopyBuffer2
import io.technoirlab.volk.vkCmdCopyBufferToImage2
import io.technoirlab.volk.vkCmdCopyImage2
import io.technoirlab.volk.vkCmdCopyImageToBuffer2
import io.technoirlab.volk.vkCmdCopyQueryPoolResults
import io.technoirlab.volk.vkCmdDispatch
import io.technoirlab.volk.vkCmdDispatchBase
import io.technoirlab.volk.vkCmdDispatchIndirect
import io.technoirlab.volk.vkCmdDraw
import io.technoirlab.volk.vkCmdDrawIndexed
import io.technoirlab.volk.vkCmdDrawIndexedIndirect
import io.technoirlab.volk.vkCmdDrawIndexedIndirectCount
import io.technoirlab.volk.vkCmdDrawIndirect
import io.technoirlab.volk.vkCmdDrawIndirectCount
import io.technoirlab.volk.vkCmdEndQuery
import io.technoirlab.volk.vkCmdEndRendering
import io.technoirlab.volk.vkCmdExecuteCommands
import io.technoirlab.volk.vkCmdFillBuffer
import io.technoirlab.volk.vkCmdPipelineBarrier2
import io.technoirlab.volk.vkCmdPushConstants
import io.technoirlab.volk.vkCmdPushDescriptorSet2
import io.technoirlab.volk.vkCmdResetEvent2
import io.technoirlab.volk.vkCmdResetQueryPool
import io.technoirlab.volk.vkCmdResolveImage2
import io.technoirlab.volk.vkCmdSetBlendConstants
import io.technoirlab.volk.vkCmdSetCullMode
import io.technoirlab.volk.vkCmdSetDepthBias
import io.technoirlab.volk.vkCmdSetDepthBiasEnable
import io.technoirlab.volk.vkCmdSetDepthBoundsTestEnable
import io.technoirlab.volk.vkCmdSetDepthCompareOp
import io.technoirlab.volk.vkCmdSetDepthTestEnable
import io.technoirlab.volk.vkCmdSetDepthWriteEnable
import io.technoirlab.volk.vkCmdSetEvent2
import io.technoirlab.volk.vkCmdSetFragmentShadingRateKHR
import io.technoirlab.volk.vkCmdSetFrontFace
import io.technoirlab.volk.vkCmdSetLineStipple
import io.technoirlab.volk.vkCmdSetLineWidth
import io.technoirlab.volk.vkCmdSetPolygonModeEXT
import io.technoirlab.volk.vkCmdSetPrimitiveRestartEnable
import io.technoirlab.volk.vkCmdSetPrimitiveTopology
import io.technoirlab.volk.vkCmdSetRasterizerDiscardEnable
import io.technoirlab.volk.vkCmdSetRenderingAttachmentLocations
import io.technoirlab.volk.vkCmdSetRenderingInputAttachmentIndices
import io.technoirlab.volk.vkCmdSetSampleLocationsEXT
import io.technoirlab.volk.vkCmdSetScissor
import io.technoirlab.volk.vkCmdSetScissorWithCount
import io.technoirlab.volk.vkCmdSetStencilCompareMask
import io.technoirlab.volk.vkCmdSetStencilOp
import io.technoirlab.volk.vkCmdSetStencilReference
import io.technoirlab.volk.vkCmdSetStencilTestEnable
import io.technoirlab.volk.vkCmdSetStencilWriteMask
import io.technoirlab.volk.vkCmdSetViewport
import io.technoirlab.volk.vkCmdSetViewportWithCount
import io.technoirlab.volk.vkCmdUpdateBuffer
import io.technoirlab.volk.vkCmdWaitEvents2
import io.technoirlab.volk.vkCmdWriteTimestamp2
import io.technoirlab.volk.vkEndCommandBuffer
import io.technoirlab.volk.vkResetCommandBuffer
import io.technoirlab.vulkan.Extent2D
import io.technoirlab.vulkan.Rect2D
import io.technoirlab.vulkan.Viewport
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.descriptor.DescriptorSet
import io.technoirlab.vulkan.from
import io.technoirlab.vulkan.image.BufferImageCopyRegion
import io.technoirlab.vulkan.image.Image
import io.technoirlab.vulkan.image.ImageBlitRegion
import io.technoirlab.vulkan.image.ImageCopyRegion
import io.technoirlab.vulkan.image.ImageResolveRegion
import io.technoirlab.vulkan.image.ImageSubresourceRange
import io.technoirlab.vulkan.image.ResolveImageModeInfo
import io.technoirlab.vulkan.image.from
import io.technoirlab.vulkan.internal.nCopies
import io.technoirlab.vulkan.internal.toVkBool32
import io.technoirlab.vulkan.pipeline.Pipeline
import io.technoirlab.vulkan.pipeline.PipelineLayout
import io.technoirlab.vulkan.pipeline.SampleLocation
import io.technoirlab.vulkan.query.QueryPool
import io.technoirlab.vulkan.resource.Buffer
import io.technoirlab.vulkan.sync.Event
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.ULongVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import kotlin.assert

/**
 * Wrapper for [VkCommandBuffer].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkCommandBuffer.html">VkCommandBuffer Manual Page</a>
 */
class CommandBuffer internal constructor(
    override val handle: VkCommandBuffer,
) : VulkanObject {

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_COMMAND_BUFFER

    /**
     * Start recording the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkBeginCommandBuffer.html">vkBeginCommandBuffer Manual Page</a>
     */
    fun begin(usageFlags: VkCommandBufferUsageFlags = 0u): Unit = memScoped {
        val beginInfo = alloc<VkCommandBufferBeginInfo> {
            sType = VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO
            flags = usageFlags
        }
        vkBeginCommandBuffer!!(handle, beginInfo.ptr)
            .checkResult("Failed to begin command buffer")
    }

    /**
     * Begin a query.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBeginQuery.html">vkCmdBeginQuery Manual Page</a>
     */
    fun beginQuery(queryPool: QueryPool, query: UInt, flags: VkQueryControlFlags = 0u) {
        vkCmdBeginQuery!!(handle, queryPool.handle, query, flags)
    }

    /**
     * Begin a dynamic render pass instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBeginRendering.html">vkCmdBeginRendering Manual Page</a>
     */
    fun beginRendering(renderingInfo: VkRenderingInfo.() -> Unit): Unit = memScoped {
        val renderingInfo = alloc<VkRenderingInfo> {
            sType = VK_STRUCTURE_TYPE_RENDERING_INFO
            renderingInfo()
        }
        vkCmdBeginRendering!!(handle, renderingInfo.ptr)
    }

    /**
     * Bind descriptor sets to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindDescriptorSets.html">vkCmdBindDescriptorSets Manual Page</a>
     */
    fun bindDescriptorSets(
        pipelineBindPoint: VkPipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS,
        layout: PipelineLayout,
        firstSet: UInt,
        descriptorSets: List<DescriptorSet>,
        dynamicOffsets: List<UInt> = emptyList(),
    ): Unit = memScoped {
        assert(descriptorSets.isNotEmpty()) { "descriptorSets must not be empty" }
        val descriptorSetHandles = allocArrayOf(descriptorSets.map { it.handle })
        val dynamicOffsetArray = if (dynamicOffsets.isNotEmpty()) {
            allocArray<UIntVar>(dynamicOffsets.size) { index ->
                value = dynamicOffsets[index]
            }
        } else {
            null
        }

        vkCmdBindDescriptorSets!!(
            handle,
            pipelineBindPoint,
            layout.handle,
            firstSet,
            descriptorSets.size.toUInt(),
            descriptorSetHandles,
            dynamicOffsets.size.toUInt(),
            dynamicOffsetArray,
        )
    }

    /**
     * Bind an index buffer to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindIndexBuffer2.html">vkCmdBindIndexBuffer2 Manual Page</a>
     */
    fun bindIndexBuffer(indexBuffer: Buffer, indexType: VkIndexType, offset: ULong = 0uL, size: ULong = VK_WHOLE_SIZE) {
        val indexSize = when (indexType) {
            VK_INDEX_TYPE_UINT8 -> 1uL
            VK_INDEX_TYPE_UINT16 -> 2uL
            VK_INDEX_TYPE_UINT32 -> 4uL
            else -> 1uL
        }
        assert(indexType == VK_INDEX_TYPE_UINT8 || indexType == VK_INDEX_TYPE_UINT16 || indexType == VK_INDEX_TYPE_UINT32) {
            "indexType must be a valid VkIndexType value and must not be VK_INDEX_TYPE_NONE_KHR"
        }
        assert(offset < indexBuffer.size) { "offset must be less than ${indexBuffer.size}" }
        assert(size == VK_WHOLE_SIZE || size % indexSize == 0uL) {
            "size must be a multiple of the size of the type indicated by indexType"
        }
        assert(size == VK_WHOLE_SIZE || size <= indexBuffer.size - offset) {
            "offset + size must be less than or equal to ${indexBuffer.size}"
        }
        vkCmdBindIndexBuffer2?.invoke(handle, indexBuffer.handle, offset, size, indexType)
            ?: vkCmdBindIndexBuffer!!(handle, indexBuffer.handle, offset, indexType)
    }

    /**
     * Bind a pipeline to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindPipeline.html">vkCmdBindPipeline Manual Page</a>
     */
    fun bindPipeline(pipelineBindPoint: VkPipelineBindPoint, pipeline: Pipeline) {
        vkCmdBindPipeline!!(handle, pipelineBindPoint, pipeline.handle)
    }

    /**
     * Bind a vertex buffer to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindVertexBuffers2.html">vkCmdBindVertexBuffers2 Manual Page</a>
     */
    fun bindVertexBuffer(
        vertexBuffer: Buffer,
        bindingIndex: UInt = 0u,
        offset: ULong = 0uL,
        size: ULong? = null,
        stride: ULong? = null,
    ): Unit = memScoped {
        assert(size == null || offset < vertexBuffer.size) {
            "offset must be less than ${vertexBuffer.size}"
        }
        assert(size == null || size == VK_WHOLE_SIZE || size <= vertexBuffer.size - offset) {
            "offset + size must be less than or equal to ${vertexBuffer.size}"
        }
        val vertexBufferVar = alloc<VkBufferVar> { value = vertexBuffer.handle }
        val offsetVar = alloc<ULongVar> { value = offset }
        val sizeVar = size?.let { alloc<ULongVar> { value = it } }
        val strideVar = stride?.let { alloc<ULongVar> { value = it } }
        vkCmdBindVertexBuffers2!!(
            handle,
            bindingIndex,
            1u,
            vertexBufferVar.ptr,
            offsetVar.ptr,
            sizeVar?.ptr,
            strideVar?.ptr,
        )
    }

    /**
     * Bind vertex buffers to the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBindVertexBuffers2.html">vkCmdBindVertexBuffers2 Manual Page</a>
     */
    fun bindVertexBuffers(
        vertexBuffers: List<Buffer>,
        firstBinding: UInt = 0u,
        offsets: List<ULong> = nCopies(vertexBuffers.size, 0uL),
        sizes: List<ULong>? = null,
        strides: List<ULong>? = null,
    ): Unit = memScoped {
        assert(sizes == null && strides == null || vertexBuffers.isNotEmpty()) { "vertexBuffers must not be empty" }
        val vertexBufferHandles = allocArrayOf(vertexBuffers.map { it.handle })
        val offsetsArray = allocArray<ULongVar>(offsets.size) { value = offsets[it] }
        val sizesArray = sizes?.let { allocArray<ULongVar>(sizes.size) { value = sizes[it] } }
        val stridesArray = strides?.let { allocArray<ULongVar>(strides.size) { value = strides[it] } }
        vkCmdBindVertexBuffers2!!(
            handle,
            firstBinding,
            vertexBuffers.size.toUInt(),
            vertexBufferHandles,
            offsetsArray,
            sizesArray,
            stridesArray,
        )
    }

    /**
     * Blit regions of an image to another image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdBlitImage2.html">vkCmdBlitImage2 Manual Page</a>
     */
    fun blitImage(
        srcImage: Image,
        srcImageLayout: VkImageLayout,
        dstImage: Image,
        dstImageLayout: VkImageLayout,
        filter: VkFilter,
        regions: List<ImageBlitRegion>,
    ): Unit = memScoped {
        assert(regions.isNotEmpty()) { "Regions must not be empty" }
        val imageBlits = allocArray<VkImageBlit2>(regions.size) {
            from(regions[it])
        }
        val blitImageInfo = alloc<VkBlitImageInfo2> {
            sType = VK_STRUCTURE_TYPE_BLIT_IMAGE_INFO_2
            this.srcImage = srcImage.handle
            this.srcImageLayout = srcImageLayout
            this.dstImage = dstImage.handle
            this.dstImageLayout = dstImageLayout
            regionCount = regions.size.toUInt()
            pRegions = imageBlits
            this.filter = filter
        }
        vkCmdBlitImage2!!(handle, blitImageInfo.ptr)
    }

    /**
     * Insert a pipeline barrier for buffer memory.
     *
     * [dependencyFlags] configures the dependency, including `VK_DEPENDENCY_BY_REGION_BIT` for framebuffer-local dependencies.
     * Inside dynamic rendering, `dynamicRenderingLocalRead` must be enabled, stage masks must contain only framebuffer-space
     * stages, and source and destination queue family indices must match.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2 Manual Page</a>
     */
    fun bufferMemoryBarrier(dependencyFlags: VkDependencyFlags = 0u, barrierInfo: VkBufferMemoryBarrier2.() -> Unit): Unit = memScoped {
        val bufferMemoryBarrier = alloc<VkBufferMemoryBarrier2> {
            sType = VK_STRUCTURE_TYPE_BUFFER_MEMORY_BARRIER_2
            barrierInfo()
        }
        pipelineBarrier {
            this.dependencyFlags = dependencyFlags
            bufferMemoryBarrierCount = 1u
            pBufferMemoryBarriers = bufferMemoryBarrier.ptr
        }
    }

    /**
     * Clear regions of color or depth/stencil attachments.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdClearAttachments.html">vkCmdClearAttachments Manual Page</a>
     */
    fun clearAttachments(attachments: List<ClearAttachment>, rects: List<ClearRect>): Unit = memScoped {
        assert(attachments.isNotEmpty()) { "Attachments must not be empty" }
        assert(rects.isNotEmpty()) { "Rectangles must not be empty" }
        val clearAttachments = allocArray<VkClearAttachment>(attachments.size) {
            from(attachments[it])
        }
        val clearRects = allocArray<VkClearRect>(rects.size) {
            from(rects[it])
        }
        vkCmdClearAttachments!!(handle, attachments.size.toUInt(), clearAttachments, rects.size.toUInt(), clearRects)
    }

    /**
     * Clear regions of a color image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdClearColorImage.html">vkCmdClearColorImage Manual Page</a>
     */
    fun clearColorImage(image: Image, imageLayout: VkImageLayout, color: ClearValue.Color, ranges: List<ImageSubresourceRange>): Unit =
        memScoped {
            assert(ranges.isNotEmpty()) { "Ranges must not be empty" }
            val clearColor = alloc<VkClearColorValue> { from(color) }
            val subresourceRanges = allocArray<VkImageSubresourceRange>(ranges.size) {
                from(ranges[it])
            }
            vkCmdClearColorImage!!(handle, image.handle, imageLayout, clearColor.ptr, ranges.size.toUInt(), subresourceRanges)
        }

    /**
     * Clear regions of a depth/stencil image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdClearDepthStencilImage.html">vkCmdClearDepthStencilImage Manual Page</a>
     */
    fun clearDepthStencilImage(
        image: Image,
        imageLayout: VkImageLayout,
        depth: Float,
        stencil: UInt,
        ranges: List<ImageSubresourceRange>,
    ): Unit = memScoped {
        assert(ranges.isNotEmpty()) { "Ranges must not be empty" }
        val clearDepthStencil = alloc<VkClearDepthStencilValue> {
            this.depth = depth
            this.stencil = stencil
        }
        val subresourceRanges = allocArray<VkImageSubresourceRange>(ranges.size) {
            from(ranges[it])
        }
        vkCmdClearDepthStencilImage!!(
            handle,
            image.handle,
            imageLayout,
            clearDepthStencil.ptr,
            ranges.size.toUInt(),
            subresourceRanges,
        )
    }

    /**
     * Copy regions between buffers.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdCopyBuffer2.html">vkCmdCopyBuffer2 Manual Page</a>
     */
    fun copyBuffer(srcBuffer: Buffer, dstBuffer: Buffer, regions: List<BufferCopyRegion>): Unit = memScoped {
        assert(regions.isNotEmpty()) { "Regions must not be empty" }
        val bufferCopies = allocArray<VkBufferCopy2>(regions.size) {
            from(regions[it])
        }
        val copyBufferInfo = alloc<VkCopyBufferInfo2> {
            sType = VK_STRUCTURE_TYPE_COPY_BUFFER_INFO_2
            this.srcBuffer = srcBuffer.handle
            this.dstBuffer = dstBuffer.handle
            regionCount = regions.size.toUInt()
            pRegions = bufferCopies
        }
        vkCmdCopyBuffer2!!(handle, copyBufferInfo.ptr)
    }

    /**
     * Copy regions from a buffer to an image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdCopyBufferToImage2.html">vkCmdCopyBufferToImage2 Manual Page</a>
     */
    fun copyBufferToImage(srcBuffer: Buffer, dstImage: Image, dstImageLayout: VkImageLayout, regions: List<BufferImageCopyRegion>): Unit =
        memScoped {
            assert(regions.isNotEmpty()) { "Regions must not be empty" }
            val bufferImageCopies = allocArray<VkBufferImageCopy2>(regions.size) {
                from(regions[it])
            }
            val copyBufferToImageInfo = alloc<VkCopyBufferToImageInfo2> {
                sType = VK_STRUCTURE_TYPE_COPY_BUFFER_TO_IMAGE_INFO_2
                this.srcBuffer = srcBuffer.handle
                this.dstImage = dstImage.handle
                this.dstImageLayout = dstImageLayout
                regionCount = regions.size.toUInt()
                pRegions = bufferImageCopies
            }
            vkCmdCopyBufferToImage2!!(handle, copyBufferToImageInfo.ptr)
        }

    /**
     * Copy regions between images.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdCopyImage2.html">vkCmdCopyImage2 Manual Page</a>
     */
    fun copyImage(
        srcImage: Image,
        srcImageLayout: VkImageLayout,
        dstImage: Image,
        dstImageLayout: VkImageLayout,
        regions: List<ImageCopyRegion>,
    ): Unit = memScoped {
        assert(regions.isNotEmpty()) { "Regions must not be empty" }
        val imageCopies = allocArray<VkImageCopy2>(regions.size) {
            from(regions[it])
        }
        val copyImageInfo = alloc<VkCopyImageInfo2> {
            sType = VK_STRUCTURE_TYPE_COPY_IMAGE_INFO_2
            this.srcImage = srcImage.handle
            this.srcImageLayout = srcImageLayout
            this.dstImage = dstImage.handle
            this.dstImageLayout = dstImageLayout
            regionCount = regions.size.toUInt()
            pRegions = imageCopies
        }
        vkCmdCopyImage2!!(handle, copyImageInfo.ptr)
    }

    /**
     * Copy regions from an image to a buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdCopyImageToBuffer2.html">vkCmdCopyImageToBuffer2 Manual Page</a>
     */
    fun copyImageToBuffer(srcImage: Image, srcImageLayout: VkImageLayout, dstBuffer: Buffer, regions: List<BufferImageCopyRegion>): Unit =
        memScoped {
            assert(regions.isNotEmpty()) { "Regions must not be empty" }
            val bufferImageCopies = allocArray<VkBufferImageCopy2>(regions.size) {
                from(regions[it])
            }
            val copyImageToBufferInfo = alloc<VkCopyImageToBufferInfo2> {
                sType = VK_STRUCTURE_TYPE_COPY_IMAGE_TO_BUFFER_INFO_2
                this.srcImage = srcImage.handle
                this.srcImageLayout = srcImageLayout
                this.dstBuffer = dstBuffer.handle
                regionCount = regions.size.toUInt()
                pRegions = bufferImageCopies
            }
            vkCmdCopyImageToBuffer2!!(handle, copyImageToBufferInfo.ptr)
        }

    /**
     * Copy results from a query pool into a buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdCopyQueryPoolResults.html">vkCmdCopyQueryPoolResults Manual Page</a>
     */
    fun copyQueryPoolResults(
        queryPool: QueryPool,
        firstQuery: UInt,
        queryCount: UInt,
        dstBuffer: Buffer,
        dstOffset: ULong,
        stride: ULong,
        flags: VkQueryResultFlags = 0u,
    ) {
        assert(dstOffset < dstBuffer.size) { "dstOffset must be less than ${dstBuffer.size}" }
        assert(queryCount <= 1u || stride != 0uL) { "stride must not be zero" }
        assert((flags and VK_QUERY_RESULT_64_BIT) != 0u || dstOffset % 4uL == 0uL) {
            "dstOffset must be a multiple of 4"
        }
        assert((flags and VK_QUERY_RESULT_64_BIT) != 0u || queryCount <= 1u || stride % 4uL == 0uL) {
            "stride must be a multiple of 4"
        }
        assert((flags and VK_QUERY_RESULT_64_BIT) == 0u || dstOffset % 8uL == 0uL) {
            "dstOffset must be a multiple of 8"
        }
        assert((flags and VK_QUERY_RESULT_64_BIT) == 0u || queryCount <= 1u || stride % 8uL == 0uL) {
            "stride must be a multiple of 8"
        }
        vkCmdCopyQueryPoolResults!!(
            handle,
            queryPool.handle,
            firstQuery,
            queryCount,
            dstBuffer.handle,
            dstOffset,
            stride,
            flags,
        )
    }

    /**
     * Finish recording the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkEndCommandBuffer.html">vkEndCommandBuffer Manual Page</a>
     */
    fun end() {
        vkEndCommandBuffer!!(handle)
            .checkResult("Failed to end command buffer")
    }

    /**
     * End a query.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdEndQuery.html">vkCmdEndQuery Manual Page</a>
     */
    fun endQuery(queryPool: QueryPool, query: UInt) {
        vkCmdEndQuery!!(handle, queryPool.handle, query)
    }

    /**
     * End a dynamic render pass instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdEndRendering.html">vkCmdEndRendering Manual Page</a>
     */
    fun endRendering() {
        vkCmdEndRendering!!(handle)
    }

    /**
     * Execute secondary command buffers from the primary command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdExecuteCommands.html">vkCmdExecuteCommands Manual Page</a>
     */
    fun executeCommands(commandBuffers: List<CommandBuffer>): Unit = memScoped {
        assert(commandBuffers.isNotEmpty()) { "commandBuffers must not be empty" }
        val commandBufferHandles = allocArrayOf(commandBuffers.map { it.handle })
        vkCmdExecuteCommands!!(handle, commandBuffers.size.toUInt(), commandBufferHandles)
    }

    /**
     * Fill a region of a buffer with a fixed value.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdFillBuffer.html">vkCmdFillBuffer Manual Page</a>
     */
    fun fillBuffer(buffer: Buffer, data: UInt, offset: ULong = 0uL, size: ULong = VK_WHOLE_SIZE) {
        assert(offset % 4uL == 0uL) { "Offset must be a multiple of 4" }
        assert(offset < buffer.size) { "Offset must be less than the buffer size" }
        if (size != VK_WHOLE_SIZE) {
            assert(size > 0uL) { "Size must be greater than 0" }
            assert(size <= buffer.size - offset) { "Fill range exceeds the buffer size" }
            assert(size % 4uL == 0uL) { "Fill size must be a multiple of 4" }
        }
        vkCmdFillBuffer!!(handle, buffer.handle, offset, size, data)
    }

    /**
     * Dispatch compute work items.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDispatch.html">vkCmdDispatch Manual Page</a>
     */
    fun dispatch(groupCountX: UInt, groupCountY: UInt = 1u, groupCountZ: UInt = 1u) {
        vkCmdDispatch!!(handle, groupCountX, groupCountY, groupCountZ)
    }

    /**
     * Dispatch compute work items with non-zero base values for the workgroup IDs.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDispatchBase.html">vkCmdDispatchBase Manual Page</a>
     */
    fun dispatchBase(
        baseGroupX: UInt,
        baseGroupY: UInt = 0u,
        baseGroupZ: UInt = 0u,
        groupCountX: UInt,
        groupCountY: UInt = 1u,
        groupCountZ: UInt = 1u,
    ) {
        vkCmdDispatchBase!!(
            handle,
            baseGroupX,
            baseGroupY,
            baseGroupZ,
            groupCountX,
            groupCountY,
            groupCountZ,
        )
    }

    /**
     * Dispatch compute work items with indirect parameters.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDispatchIndirect.html">vkCmdDispatchIndirect Manual Page</a>
     */
    fun dispatchIndirect(buffer: Buffer, offset: ULong = 0uL) {
        assert(offset % 4uL == 0uL) { "offset must be a multiple of 4" }
        assert(buffer.size >= DISPATCH_INDIRECT_COMMAND_SIZE && offset <= buffer.size - DISPATCH_INDIRECT_COMMAND_SIZE) {
            "offset + sizeof(VkDispatchIndirectCommand) must be less than or equal to ${buffer.size}"
        }
        vkCmdDispatchIndirect!!(handle, buffer.handle, offset)
    }

    /**
     * Draw primitives.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDraw.html">vkCmdDraw Manual Page</a>
     */
    fun draw(vertexCount: UInt, instanceCount: UInt = 1u, firstVertex: UInt = 0u, firstInstance: UInt = 0u) {
        vkCmdDraw!!(handle, vertexCount, instanceCount, firstVertex, firstInstance)
    }

    /**
     * Draw primitives with indexed vertices.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndexed.html">vkCmdDrawIndexed Manual Page</a>
     */
    fun drawIndexed(indexCount: UInt, instanceCount: UInt = 1u, firstIndex: UInt = 0u, vertexOffset: Int = 0, firstInstance: UInt = 0u) {
        vkCmdDrawIndexed!!(handle, indexCount, instanceCount, firstIndex, vertexOffset, firstInstance)
    }

    /**
     * Draw primitives with indexed vertices indirectly.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndexedIndirect.html">vkCmdDrawIndexedIndirect Manual Page</a>
     */
    fun drawIndexedIndirect(buffer: Buffer, offset: ULong, drawCount: UInt, stride: UInt) {
        assert(offset % 4uL == 0uL) { "offset must be a multiple of 4" }
        assert(drawCount <= 1u || stride % 4u == 0u && stride >= 20u) {
            "stride must be a multiple of 4 and must be greater than or equal to sizeof(VkDrawIndexedIndirectCommand)"
        }
        vkCmdDrawIndexedIndirect!!(handle, buffer.handle, offset, drawCount, stride)
    }

    /**
     * Draw indexed primitives indirectly with the draw count read from a buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndexedIndirectCount.html">vkCmdDrawIndexedIndirectCount Manual Page</a>
     */
    fun drawIndexedIndirectCount(
        buffer: Buffer,
        offset: ULong,
        countBuffer: Buffer,
        countBufferOffset: ULong,
        maxDrawCount: UInt,
        stride: UInt,
    ) {
        assert(offset % 4uL == 0uL) { "offset must be a multiple of 4" }
        assert(countBufferOffset % 4uL == 0uL) { "countBufferOffset must be a multiple of 4" }
        assert(stride % 4u == 0u && stride >= 20u) {
            "stride must be a multiple of 4 and must be greater than or equal to sizeof(VkDrawIndexedIndirectCommand)"
        }
        assert(countBuffer.size >= DRAW_COUNT_SIZE && countBufferOffset <= countBuffer.size - DRAW_COUNT_SIZE) {
            "countBufferOffset + sizeof(uint32_t) must be less than or equal to ${countBuffer.size}"
        }
        vkCmdDrawIndexedIndirectCount!!(
            handle,
            buffer.handle,
            offset,
            countBuffer.handle,
            countBufferOffset,
            maxDrawCount,
            stride,
        )
    }

    /**
     * Draw primitives indirectly.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndirect.html">vkCmdDrawIndirect Manual Page</a>
     */
    fun drawIndirect(buffer: Buffer, offset: ULong, drawCount: UInt, stride: UInt) {
        assert(offset % 4uL == 0uL) { "offset must be a multiple of 4" }
        assert(drawCount <= 1u || stride % 4u == 0u && stride >= 16u) {
            "stride must be a multiple of 4 and must be greater than or equal to sizeof(VkDrawIndirectCommand)"
        }
        vkCmdDrawIndirect!!(handle, buffer.handle, offset, drawCount, stride)
    }

    /**
     * Draw primitives indirectly with the draw count read from a buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawIndirectCount.html">vkCmdDrawIndirectCount Manual Page</a>
     */
    fun drawIndirectCount(buffer: Buffer, offset: ULong, countBuffer: Buffer, countBufferOffset: ULong, maxDrawCount: UInt, stride: UInt) {
        assert(offset % 4uL == 0uL) { "offset must be a multiple of 4" }
        assert(countBufferOffset % 4uL == 0uL) { "countBufferOffset must be a multiple of 4" }
        assert(stride % 4u == 0u && stride >= 16u) {
            "stride must be a multiple of 4 and must be greater than or equal to sizeof(VkDrawIndirectCommand)"
        }
        assert(countBuffer.size >= DRAW_COUNT_SIZE && countBufferOffset <= countBuffer.size - DRAW_COUNT_SIZE) {
            "countBufferOffset + sizeof(uint32_t) must be less than or equal to ${countBuffer.size}"
        }
        vkCmdDrawIndirectCount!!(
            handle,
            buffer.handle,
            offset,
            countBuffer.handle,
            countBufferOffset,
            maxDrawCount,
            stride,
        )
    }

    /**
     * Insert a pipeline barrier for image memory.
     *
     * [dependencyFlags] configures the dependency, including `VK_DEPENDENCY_BY_REGION_BIT` for framebuffer-local dependencies.
     * Inside dynamic rendering, `dynamicRenderingLocalRead` must be enabled, stage masks must contain only framebuffer-space
     * stages, and source and destination queue family indices must match. Image layouts must remain unchanged, and attachments
     * must use `VK_IMAGE_LAYOUT_RENDERING_LOCAL_READ` or `VK_IMAGE_LAYOUT_GENERAL`.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2 Manual Page</a>
     */
    fun imageMemoryBarrier(dependencyFlags: VkDependencyFlags = 0u, barrierInfo: VkImageMemoryBarrier2.() -> Unit): Unit = memScoped {
        val imageMemoryBarrier = alloc<VkImageMemoryBarrier2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_MEMORY_BARRIER_2
            barrierInfo()
        }
        pipelineBarrier {
            this.dependencyFlags = dependencyFlags
            imageMemoryBarrierCount = 1u
            pImageMemoryBarriers = imageMemoryBarrier.ptr
        }
    }

    /**
     * Insert a pipeline barrier.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPipelineBarrier2.html">vkCmdPipelineBarrier2 Manual Page</a>
     */
    fun pipelineBarrier(dependencyInfo: VkDependencyInfo.() -> Unit): Unit = memScoped {
        val dependencyInfo = alloc<VkDependencyInfo> {
            sType = VK_STRUCTURE_TYPE_DEPENDENCY_INFO
            dependencyInfo()
        }
        vkCmdPipelineBarrier2!!(handle, dependencyInfo.ptr)
    }

    /**
     * Update the values of push constants.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPushConstants.html">vkCmdPushConstants Manual Page</a>
     */
    fun pushConstants(layout: PipelineLayout, stageFlags: VkShaderStageFlags, values: ByteArray, offset: UInt = 0u) {
        assert(offset % 4u == 0u) { "offset must be a multiple of 4" }
        assert(values.size > 0) { "values must not be empty" }
        assert(values.size % 4 == 0) { "values.size must be a multiple of 4" }
        assert(stageFlags != 0u) { "stageFlags must not be 0" }
        values.usePinned {
            vkCmdPushConstants!!(
                handle,
                layout.handle,
                stageFlags,
                offset,
                values.size.toUInt(),
                it.addressOf(0),
            )
        }
    }

    /**
     * Pushes descriptor updates into the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdPushDescriptorSet2.html">vkCmdPushDescriptorSet2 Manual Page</a>
     */
    fun pushDescriptorSet(pushInfo: VkPushDescriptorSetInfo.() -> Unit): Unit = memScoped {
        val pushDescriptorSetInfo = alloc<VkPushDescriptorSetInfo> {
            sType = VK_STRUCTURE_TYPE_PUSH_DESCRIPTOR_SET_INFO
            pushInfo()
        }
        vkCmdPushDescriptorSet2!!(handle, pushDescriptorSetInfo.ptr)
    }

    /**
     * Reset the command buffer to the initial state.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkResetCommandBuffer.html">vkResetCommandBuffer Manual Page</a>
     */
    fun reset(flags: VkCommandBufferResetFlags = 0u) {
        vkResetCommandBuffer!!(handle, flags)
            .checkResult("Failed to reset command buffer")
    }

    /**
     * Reset an event to a non-signaled state.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdResetEvent2.html">vkCmdResetEvent2 Manual Page</a>
     */
    fun resetEvent(event: Event, stageMask: VkPipelineStageFlags2) {
        assert((stageMask and VK_PIPELINE_STAGE_2_HOST_BIT) == 0uL) {
            "stageMask must not include VK_PIPELINE_STAGE_2_HOST_BIT"
        }
        vkCmdResetEvent2!!(handle, event.handle, stageMask)
    }

    /**
     * Reset a range of queries in a query pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdResetQueryPool.html">vkCmdResetQueryPool Manual Page</a>
     */
    fun resetQueryPool(queryPool: QueryPool, firstQuery: UInt, queryCount: UInt) {
        vkCmdResetQueryPool!!(handle, queryPool.handle, firstQuery, queryCount)
    }

    /**
     * Resolve regions of a multisampled image into another image.
     *
     * [modeInfo] requires `VK_KHR_maintenance10` and its `maintenance10` feature. When `null`,
     * no resolve-mode extension is chained. Depth/stencil resolves require a non-null [modeInfo].
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdResolveImage2.html">vkCmdResolveImage2 Manual Page</a>
     */
    fun resolveImage(
        srcImage: Image,
        srcImageLayout: VkImageLayout,
        dstImage: Image,
        dstImageLayout: VkImageLayout,
        regions: List<ImageResolveRegion>,
        modeInfo: ResolveImageModeInfo? = null,
    ): Unit = memScoped {
        assert(regions.isNotEmpty()) { "Regions must not be empty" }
        val imageResolves = allocArray<VkImageResolve2>(regions.size) {
            from(regions[it])
        }
        val resolveImageModeInfo = modeInfo?.let { info ->
            alloc<VkResolveImageModeInfoKHR> { from(info) }
        }
        val resolveImageInfo = alloc<VkResolveImageInfo2> {
            sType = VK_STRUCTURE_TYPE_RESOLVE_IMAGE_INFO_2
            pNext = resolveImageModeInfo?.ptr
            this.srcImage = srcImage.handle
            this.srcImageLayout = srcImageLayout
            this.dstImage = dstImage.handle
            this.dstImageLayout = dstImageLayout
            regionCount = regions.size.toUInt()
            pRegions = imageResolves
        }
        vkCmdResolveImage2!!(handle, resolveImageInfo.ptr)
    }

    /**
     * Set blend constants dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetBlendConstants.html">vkCmdSetBlendConstants Manual Page</a>
     */
    fun setBlendConstants(r: Float, g: Float, b: Float, a: Float) {
        val blendConstants = floatArrayOf(r, g, b, a)
        blendConstants.usePinned {
            vkCmdSetBlendConstants!!(handle, it.addressOf(0))
        }
    }

    /**
     * Set cull mode dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetCullMode.html">vkCmdSetCullMode Manual Page</a>
     */
    fun setCullMode(cullMode: VkCullModeFlags) {
        vkCmdSetCullMode!!(handle, cullMode)
    }

    /**
     * Set an event with memory and execution dependencies.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetEvent2.html">vkCmdSetEvent2 Manual Page</a>
     */
    fun setEvent(event: Event, dependencyInfo: VkDependencyInfo.() -> Unit): Unit = memScoped {
        val dep = alloc<VkDependencyInfo> {
            sType = VK_STRUCTURE_TYPE_DEPENDENCY_INFO
            dependencyInfo()
        }
        vkCmdSetEvent2!!(handle, event.handle, dep.ptr)
    }

    /**
     * Set depth bias factors and clamp dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBias.html">vkCmdSetDepthBias Manual Page</a>
     */
    fun setDepthBias(constantFactor: Float, clamp: Float, slopeFactor: Float) {
        vkCmdSetDepthBias!!(handle, constantFactor, clamp, slopeFactor)
    }

    /**
     * Control whether to bias fragment depth values dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBiasEnable.html">vkCmdSetDepthBiasEnable Manual Page</a>
     */
    fun setDepthBiasEnable(enable: Boolean) {
        vkCmdSetDepthBiasEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable depth bounds testing dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthBoundsTestEnable.html">vkCmdSetDepthBoundsTestEnable Manual Page</a>
     */
    fun setDepthBoundsTestEnable(enable: Boolean) {
        vkCmdSetDepthBoundsTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable depth testing dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthTestEnable.html">vkCmdSetDepthTestEnable Manual Page</a>
     */
    fun setDepthTestEnable(enable: Boolean) {
        vkCmdSetDepthTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Enable or disable writes to the depth buffer dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthWriteEnable.html">vkCmdSetDepthWriteEnable Manual Page</a>
     */
    fun setDepthWriteEnable(enable: Boolean) {
        vkCmdSetDepthWriteEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set depth comparison operator dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetDepthCompareOp.html">vkCmdSetDepthCompareOp Manual Page</a>
     */
    fun setDepthCompareOp(compareOp: VkCompareOp) {
        vkCmdSetDepthCompareOp!!(handle, compareOp)
    }

    /**
     * Set the fragment shading rate and combiner operations dynamically.
     *
     * Requires the `VK_KHR_fragment_shading_rate` extension and at least one of its
     * `pipelineFragmentShadingRate`, `primitiveFragmentShadingRate`, or `attachmentFragmentShadingRate` features.
     * Applies to shader objects and pipelines with dynamic fragment shading rate enabled.
     *
     * @param fragmentSize Fragment width and height in pixels, each equal to 1, 2, or 4.
     * @param primitiveCombinerOp Operation combining the pipeline and primitive shading rates.
     * @param attachmentCombinerOp Operation combining that result with the attachment shading rate.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetFragmentShadingRateKHR.html">vkCmdSetFragmentShadingRateKHR Manual Page</a>
     */
    fun setFragmentShadingRate(
        fragmentSize: Extent2D,
        primitiveCombinerOp: VkFragmentShadingRateCombinerOpKHR = VK_FRAGMENT_SHADING_RATE_COMBINER_OP_KEEP_KHR,
        attachmentCombinerOp: VkFragmentShadingRateCombinerOpKHR = VK_FRAGMENT_SHADING_RATE_COMBINER_OP_KEEP_KHR,
    ): Unit = memScoped {
        assert(fragmentSize.width == 1u || fragmentSize.width == 2u || fragmentSize.width == 4u) {
            "fragment width must be 1, 2, or 4"
        }
        assert(fragmentSize.height == 1u || fragmentSize.height == 2u || fragmentSize.height == 4u) {
            "fragment height must be 1, 2, or 4"
        }
        val size = alloc<VkExtent2D> {
            width = fragmentSize.width
            height = fragmentSize.height
        }
        val combinerOps = allocArray<VkFragmentShadingRateCombinerOpKHRVar>(2) { index: Int ->
            value = if (index == 0) primitiveCombinerOp else attachmentCombinerOp
        }
        vkCmdSetFragmentShadingRateKHR!!(handle, size.ptr, combinerOps)
    }

    /**
     * Set front face orientation dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetFrontFace.html">vkCmdSetFrontFace Manual Page</a>
     */
    fun setFrontFace(frontFace: VkFrontFace) {
        vkCmdSetFrontFace!!(handle, frontFace)
    }

    /**
     * Set the line stipple repeat factor and bit pattern dynamically.
     *
     * @param factor Repeat factor in the range from 1 through 256.
     * @param pattern The 16-bit line stipple pattern.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLineStipple.html">vkCmdSetLineStipple Manual Page</a>
     */
    fun setLineStipple(factor: UInt, pattern: UShort) {
        assert(factor in 1u..256u) { "factor must be between 1 and 256" }
        vkCmdSetLineStipple!!(handle, factor, pattern)
    }

    /**
     * Set line width dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetLineWidth.html">vkCmdSetLineWidth Manual Page</a>
     */
    fun setLineWidth(lineWidth: Float) {
        vkCmdSetLineWidth!!(handle, lineWidth)
    }

    /**
     * Set polygon mode dynamically for the command buffer.
     *
     * Requires `VK_EXT_extended_dynamic_state3` or `VK_EXT_shader_object` extension.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPolygonModeEXT.html">vkCmdSetPolygonModeEXT Manual Page</a>
     */
    fun setPolygonMode(polygonMode: VkPolygonMode) {
        vkCmdSetPolygonModeEXT!!(handle, polygonMode)
    }

    /**
     * Set primitive assembly restart state dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPrimitiveRestartEnable.html">vkCmdSetPrimitiveRestartEnable Manual Page</a>
     */
    fun setPrimitiveRestartEnable(enable: Boolean) {
        vkCmdSetPrimitiveRestartEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set primitive topology dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetPrimitiveTopology.html">vkCmdSetPrimitiveTopology Manual Page</a>
     */
    fun setPrimitiveTopology(topology: VkPrimitiveTopology) {
        vkCmdSetPrimitiveTopology!!(handle, topology)
    }

    /**
     * Enable or disable rasterizer discard dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetRasterizerDiscardEnable.html">vkCmdSetRasterizerDiscardEnable Manual Page</a>
     */
    fun setRasterizerDiscardEnable(enable: Boolean) {
        vkCmdSetRasterizerDiscardEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set the fragment output locations used by color attachments in the current dynamic rendering instance.
     *
     * [colorAttachmentLocations] must have the same number of entries as the color attachments passed to
     * [beginRendering]. Non-[VK_ATTACHMENT_UNUSED] locations must be unique. Vulkan 1.4 and the
     * `dynamicRenderingLocalRead` feature are required.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetRenderingAttachmentLocations.html">vkCmdSetRenderingAttachmentLocations Manual Page</a>
     */
    fun setRenderingAttachmentLocations(colorAttachmentLocations: List<UInt>): Unit = memScoped {
        val locations = if (colorAttachmentLocations.isNotEmpty()) {
            allocArray<UIntVar>(colorAttachmentLocations.size) { index ->
                value = colorAttachmentLocations[index]
            }
        } else {
            null
        }
        val locationInfo = alloc<VkRenderingAttachmentLocationInfo> {
            sType = VK_STRUCTURE_TYPE_RENDERING_ATTACHMENT_LOCATION_INFO
            colorAttachmentCount = colorAttachmentLocations.size.toUInt()
            pColorAttachmentLocations = locations
        }
        vkCmdSetRenderingAttachmentLocations!!(handle, locationInfo.ptr)
    }

    /**
     * Set the shader input attachment indices used in the current dynamic rendering instance.
     *
     * [colorAttachmentInputIndices] must have the same number of entries as the color attachments passed to
     * [beginRendering]. Non-[VK_ATTACHMENT_UNUSED] color indices must be unique and must differ from either
     * optional depth or stencil index. Vulkan 1.4 and the `dynamicRenderingLocalRead` feature are required.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetRenderingInputAttachmentIndices.html">vkCmdSetRenderingInputAttachmentIndices Manual Page</a>
     */
    fun setRenderingInputAttachmentIndices(
        colorAttachmentInputIndices: List<UInt>,
        depthInputAttachmentIndex: UInt? = null,
        stencilInputAttachmentIndex: UInt? = null,
    ): Unit = memScoped {
        val mappedColorIndices = colorAttachmentInputIndices.filter { it != VK_ATTACHMENT_UNUSED }
        assert(
            depthInputAttachmentIndex == null ||
                depthInputAttachmentIndex == VK_ATTACHMENT_UNUSED ||
                depthInputAttachmentIndex !in mappedColorIndices,
        ) {
            "The depth input attachment index must differ from all mapped color attachment indices"
        }
        assert(
            stencilInputAttachmentIndex == null ||
                stencilInputAttachmentIndex == VK_ATTACHMENT_UNUSED ||
                stencilInputAttachmentIndex !in mappedColorIndices,
        ) {
            "The stencil input attachment index must differ from all mapped color attachment indices"
        }

        val colorIndices = if (colorAttachmentInputIndices.isNotEmpty()) {
            allocArray<UIntVar>(colorAttachmentInputIndices.size) { index ->
                value = colorAttachmentInputIndices[index]
            }
        } else {
            null
        }
        val depthIndex = depthInputAttachmentIndex?.let { alloc<UIntVar> { value = it } }
        val stencilIndex = stencilInputAttachmentIndex?.let { alloc<UIntVar> { value = it } }
        val inputAttachmentIndexInfo = alloc<VkRenderingInputAttachmentIndexInfo> {
            sType = VK_STRUCTURE_TYPE_RENDERING_INPUT_ATTACHMENT_INDEX_INFO
            colorAttachmentCount = colorAttachmentInputIndices.size.toUInt()
            pColorAttachmentInputIndices = colorIndices
            pDepthInputAttachmentIndex = depthIndex?.ptr
            pStencilInputAttachmentIndex = stencilIndex?.ptr
        }
        vkCmdSetRenderingInputAttachmentIndices!!(handle, inputAttachmentIndexInfo.ptr)
    }

    /**
     * Set custom sample locations dynamically.
     *
     * Requires the `VK_EXT_sample_locations` extension.
     *
     * @param samples Number of sample locations per pixel.
     * @param gridSize Dimensions of the sample location grid.
     * @param sampleLocations Locations ordered by pixel in row-major order, then by sample index within each pixel.
     * The list size must equal [samples] multiplied by the grid width and height.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetSampleLocationsEXT.html">vkCmdSetSampleLocationsEXT Manual Page</a>
     */
    fun setSampleLocations(samples: VkSampleCountFlagBits, gridSize: Extent2D, sampleLocations: List<SampleLocation>): Unit = memScoped {
        val pixelCount = gridSize.width.toULong() * gridSize.height.toULong()
        assert(
            samples > 0u && pixelCount <= Int.MAX_VALUE.toULong() &&
                sampleLocations.size.toULong() == samples.toULong() * pixelCount,
        ) { "sampleLocations must contain samples times grid width times grid height entries" }
        val locations = if (sampleLocations.isNotEmpty()) {
            allocArray<VkSampleLocationEXT>(sampleLocations.size) { index ->
                x = sampleLocations[index].x
                y = sampleLocations[index].y
            }
        } else {
            null
        }
        val sampleLocationsInfo = alloc<VkSampleLocationsInfoEXT> {
            sType = VK_STRUCTURE_TYPE_SAMPLE_LOCATIONS_INFO_EXT
            pNext = null
            sampleLocationsPerPixel = samples
            sampleLocationGridSize.width = gridSize.width
            sampleLocationGridSize.height = gridSize.height
            sampleLocationsCount = sampleLocations.size.toUInt()
            pSampleLocations = locations
        }
        vkCmdSetSampleLocationsEXT!!(handle, sampleLocationsInfo.ptr)
    }

    /**
     * Set the scissor rectangle at index zero dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetScissor.html">vkCmdSetScissor Manual Page</a>
     */
    fun setScissor(scissor: Rect2D): Unit = memScoped {
        val scissor = alloc<VkRect2D> { from(scissor) }
        vkCmdSetScissor!!(handle, 0u, 1u, scissor.ptr)
    }

    /**
     * Set the scissor count and scissor rectangular bounds dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetScissorWithCount.html">vkCmdSetScissorWithCount Manual Page</a>
     */
    fun setScissorWithCount(scissors: List<Rect2D>): Unit = memScoped {
        assert(scissors.isNotEmpty()) { "scissors must not be empty" }
        val count = scissors.size.toUInt()
        val scissors = allocArray<VkRect2D>(scissors.size) { from(scissors[it]) }
        vkCmdSetScissorWithCount!!(handle, count, scissors)
    }

    /**
     * Set stencil test compare mask dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilCompareMask.html">vkCmdSetStencilCompareMask Manual Page</a>
     */
    fun setStencilCompareMask(faceMask: VkStencilFaceFlags, compareMask: UInt) {
        assert(faceMask != 0u) { "faceMask must not be 0" }
        vkCmdSetStencilCompareMask!!(handle, faceMask, compareMask)
    }

    /**
     * Set stencil test actions dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilOp.html">vkCmdSetStencilOp Manual Page</a>
     */
    fun setStencilOp(
        faceMask: VkStencilFaceFlags,
        failOp: VkStencilOp,
        passOp: VkStencilOp,
        depthFailOp: VkStencilOp,
        compareOp: VkCompareOp,
    ) {
        assert(faceMask != 0u) { "faceMask must not be 0" }
        vkCmdSetStencilOp!!(handle, faceMask, failOp, passOp, depthFailOp, compareOp)
    }

    /**
     * Set stencil reference value dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilReference.html">vkCmdSetStencilReference Manual Page</a>
     */
    fun setStencilReference(faceMask: VkStencilFaceFlags, reference: UInt) {
        assert(faceMask != 0u) { "faceMask must not be 0" }
        vkCmdSetStencilReference!!(handle, faceMask, reference)
    }

    /**
     * Enable or disable stencil test dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilTestEnable.html">vkCmdSetStencilTestEnable Manual Page</a>
     */
    fun setStencilTestEnable(enable: Boolean) {
        vkCmdSetStencilTestEnable!!(handle, enable.toVkBool32())
    }

    /**
     * Set stencil write mask dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetStencilWriteMask.html">vkCmdSetStencilWriteMask Manual Page</a>
     */
    fun setStencilWriteMask(faceMask: VkStencilFaceFlags, writeMask: UInt) {
        assert(faceMask != 0u) { "faceMask must not be 0" }
        vkCmdSetStencilWriteMask!!(handle, faceMask, writeMask)
    }

    /**
     * Set viewport transformation parameters at index zero dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetViewport.html">vkCmdSetViewport Manual Page</a>
     */
    fun setViewport(viewport: Viewport): Unit = memScoped {
        val vp = alloc<VkViewport> { from(viewport) }
        vkCmdSetViewport!!(handle, 0u, 1u, vp.ptr)
    }

    /**
     * Set the viewport count and viewports dynamically for the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdSetViewportWithCount.html">vkCmdSetViewportWithCount Manual Page</a>
     */
    fun setViewportWithCount(viewports: List<Viewport>): Unit = memScoped {
        assert(viewports.isNotEmpty()) { "viewports must not be empty" }
        val count = viewports.size.toUInt()
        val viewports = allocArray<VkViewport>(viewports.size) { from(viewports[it]) }
        vkCmdSetViewportWithCount!!(handle, count, viewports)
    }

    /**
     * Update a buffer's contents from host data embedded in the command buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdUpdateBuffer.html">vkCmdUpdateBuffer Manual Page</a>
     */
    fun updateBuffer(buffer: Buffer, data: ByteArray, offset: ULong = 0uL) {
        assert(data.isNotEmpty()) { "Data must not be empty" }
        assert(data.size % 4 == 0) { "Data size must be a multiple of 4" }
        assert(data.size <= MAX_UPDATE_BUFFER_SIZE) { "Data size must not exceed $MAX_UPDATE_BUFFER_SIZE bytes" }
        assert(offset % 4uL == 0uL) { "Offset must be a multiple of 4" }
        assert(offset <= buffer.size && data.size.toULong() <= buffer.size - offset) { "Update range exceeds the buffer size" }
        data.usePinned {
            vkCmdUpdateBuffer!!(handle, buffer.handle, offset, data.size.toULong(), it.addressOf(0))
        }
    }

    /**
     * Make the command buffer wait for one or more events to become signaled.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdWaitEvents2.html">vkCmdWaitEvents2 Manual Page</a>
     */
    fun waitEvents(events: List<Event>, dependencyInfos: VkDependencyInfo.(UInt) -> Unit): Unit = memScoped {
        assert(events.isNotEmpty()) { "events must not be empty" }
        val eventsArray = allocArrayOf(events.map { it.handle })
        val deps = allocArray<VkDependencyInfo>(events.size) { index: Int ->
            sType = VK_STRUCTURE_TYPE_DEPENDENCY_INFO
            dependencyInfos(index.toUInt())
        }
        vkCmdWaitEvents2!!(handle, events.size.toUInt(), eventsArray, deps)
    }

    /**
     * Write a device timestamp into a query object.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdWriteTimestamp2.html">vkCmdWriteTimestamp2 Manual Page</a>
     */
    fun writeTimestamp(stage: VkPipelineStageFlags2, queryPool: QueryPool, query: UInt) {
        assert(stage.countOneBits() == 1) { "stage must only include a single pipeline stage" }
        vkCmdWriteTimestamp2!!(handle, stage, queryPool.handle, query)
    }

    private companion object {
        const val DISPATCH_INDIRECT_COMMAND_SIZE = 12uL
        const val DRAW_COUNT_SIZE = 4uL
        const val MAX_UPDATE_BUFFER_SIZE = 65_536
    }
}
