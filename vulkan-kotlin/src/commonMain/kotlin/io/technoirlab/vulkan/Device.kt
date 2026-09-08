package io.technoirlab.vulkan

import io.technoirlab.volk.VK_KHR_SWAPCHAIN_EXTENSION_NAME
import io.technoirlab.volk.VK_OBJECT_TYPE_DEVICE
import io.technoirlab.volk.VK_SEMAPHORE_TYPE_BINARY
import io.technoirlab.volk.VK_SEMAPHORE_TYPE_TIMELINE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_SUPPORT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_EVENT_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_SUBRESOURCE_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_CREATE_FLAGS_2_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_RENDERING_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SEMAPHORE_TYPE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SUBRESOURCE_HOST_MEMCPY_SIZE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SUBRESOURCE_LAYOUT_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR
import io.technoirlab.volk.VK_TRUE
import io.technoirlab.volk.VkBufferCreateInfo
import io.technoirlab.volk.VkBufferVar
import io.technoirlab.volk.VkBufferViewCreateInfo
import io.technoirlab.volk.VkBufferViewVar
import io.technoirlab.volk.VkCommandPoolCreateFlags
import io.technoirlab.volk.VkCommandPoolCreateInfo
import io.technoirlab.volk.VkCommandPoolVar
import io.technoirlab.volk.VkComputePipelineCreateInfo
import io.technoirlab.volk.VkCopyDescriptorSet
import io.technoirlab.volk.VkDescriptorPoolCreateInfo
import io.technoirlab.volk.VkDescriptorPoolVar
import io.technoirlab.volk.VkDescriptorSetLayoutCreateInfo
import io.technoirlab.volk.VkDescriptorSetLayoutSupport
import io.technoirlab.volk.VkDescriptorSetLayoutVar
import io.technoirlab.volk.VkDevice
import io.technoirlab.volk.VkDeviceMemoryVar
import io.technoirlab.volk.VkEventCreateFlags
import io.technoirlab.volk.VkEventCreateInfo
import io.technoirlab.volk.VkEventVar
import io.technoirlab.volk.VkFenceCreateInfo
import io.technoirlab.volk.VkFenceVar
import io.technoirlab.volk.VkGraphicsPipelineCreateInfo
import io.technoirlab.volk.VkImageAspectFlags
import io.technoirlab.volk.VkImageCreateInfo
import io.technoirlab.volk.VkImageSubresource2
import io.technoirlab.volk.VkImageVar
import io.technoirlab.volk.VkImageViewCreateInfo
import io.technoirlab.volk.VkImageViewVar
import io.technoirlab.volk.VkMemoryAllocateInfo
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPipelineCacheCreateInfo
import io.technoirlab.volk.VkPipelineCacheVar
import io.technoirlab.volk.VkPipelineColorBlendStateCreateInfo
import io.technoirlab.volk.VkPipelineCreateFlags2
import io.technoirlab.volk.VkPipelineCreateFlags2CreateInfo
import io.technoirlab.volk.VkPipelineDepthStencilStateCreateInfo
import io.technoirlab.volk.VkPipelineDynamicStateCreateInfo
import io.technoirlab.volk.VkPipelineInputAssemblyStateCreateInfo
import io.technoirlab.volk.VkPipelineLayoutCreateInfo
import io.technoirlab.volk.VkPipelineLayoutVar
import io.technoirlab.volk.VkPipelineMultisampleStateCreateInfo
import io.technoirlab.volk.VkPipelineRasterizationStateCreateInfo
import io.technoirlab.volk.VkPipelineRenderingCreateInfo
import io.technoirlab.volk.VkPipelineShaderStageCreateInfo
import io.technoirlab.volk.VkPipelineTessellationStateCreateInfo
import io.technoirlab.volk.VkPipelineVar
import io.technoirlab.volk.VkPipelineVertexInputStateCreateInfo
import io.technoirlab.volk.VkPipelineViewportStateCreateInfo
import io.technoirlab.volk.VkQueryPoolCreateInfo
import io.technoirlab.volk.VkQueryPoolVar
import io.technoirlab.volk.VkQueueVar
import io.technoirlab.volk.VkSamplerCreateInfo
import io.technoirlab.volk.VkSamplerVar
import io.technoirlab.volk.VkSemaphoreCreateInfo
import io.technoirlab.volk.VkSemaphoreType
import io.technoirlab.volk.VkSemaphoreTypeCreateInfo
import io.technoirlab.volk.VkSemaphoreVar
import io.technoirlab.volk.VkShaderModuleCreateInfo
import io.technoirlab.volk.VkShaderModuleVar
import io.technoirlab.volk.VkSubresourceHostMemcpySize
import io.technoirlab.volk.VkSubresourceLayout2
import io.technoirlab.volk.VkSwapchainCreateInfoKHR
import io.technoirlab.volk.VkSwapchainKHRVar
import io.technoirlab.volk.VkWriteDescriptorSet
import io.technoirlab.volk.vkAllocateMemory
import io.technoirlab.volk.vkCreateBuffer
import io.technoirlab.volk.vkCreateBufferView
import io.technoirlab.volk.vkCreateCommandPool
import io.technoirlab.volk.vkCreateComputePipelines
import io.technoirlab.volk.vkCreateDescriptorPool
import io.technoirlab.volk.vkCreateDescriptorSetLayout
import io.technoirlab.volk.vkCreateEvent
import io.technoirlab.volk.vkCreateFence
import io.technoirlab.volk.vkCreateGraphicsPipelines
import io.technoirlab.volk.vkCreateImage
import io.technoirlab.volk.vkCreateImageView
import io.technoirlab.volk.vkCreatePipelineCache
import io.technoirlab.volk.vkCreatePipelineLayout
import io.technoirlab.volk.vkCreateQueryPool
import io.technoirlab.volk.vkCreateSampler
import io.technoirlab.volk.vkCreateSemaphore
import io.technoirlab.volk.vkCreateShaderModule
import io.technoirlab.volk.vkCreateSwapchainKHR
import io.technoirlab.volk.vkDestroyDevice
import io.technoirlab.volk.vkDeviceWaitIdle
import io.technoirlab.volk.vkGetDescriptorSetLayoutSupport
import io.technoirlab.volk.vkGetDeviceQueue
import io.technoirlab.volk.vkGetImageSubresourceLayout2
import io.technoirlab.volk.vkUpdateDescriptorSets
import io.technoirlab.volk.volkLoadDevice
import io.technoirlab.vulkan.command.CommandPool
import io.technoirlab.vulkan.descriptor.DescriptorPool
import io.technoirlab.vulkan.descriptor.DescriptorSetLayout
import io.technoirlab.vulkan.image.Image
import io.technoirlab.vulkan.image.ImageSubresourceLayout
import io.technoirlab.vulkan.image.ImageView
import io.technoirlab.vulkan.image.toImageSubresourceLayout
import io.technoirlab.vulkan.memory.DeviceMemory
import io.technoirlab.vulkan.pipeline.Pipeline
import io.technoirlab.vulkan.pipeline.PipelineCache
import io.technoirlab.vulkan.pipeline.PipelineLayout
import io.technoirlab.vulkan.pipeline.ShaderModule
import io.technoirlab.vulkan.presentation.Swapchain
import io.technoirlab.vulkan.query.QueryPool
import io.technoirlab.vulkan.resource.Buffer
import io.technoirlab.vulkan.resource.BufferView
import io.technoirlab.vulkan.resource.Sampler
import io.technoirlab.vulkan.sync.Event
import io.technoirlab.vulkan.sync.Fence
import io.technoirlab.vulkan.sync.Semaphore
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import kotlin.assert

/**
 * Wrapper for [VkDevice].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDevice.html">VkDevice Manual Page</a>
 */
class Device internal constructor(
    override val handle: VkDevice,
    val enabledExtensions: Set<String>,
) : VulkanObject,
    AutoCloseable {

    init {
        volkLoadDevice(handle)
    }

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_DEVICE

    /**
     * Allocate device memory.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAllocateMemory.html">vkAllocateMemory Manual Page</a>
     */
    fun allocateMemory(allocateInfo: VkMemoryAllocateInfo.() -> Unit): DeviceMemory = memScoped {
        val memoryAllocateInfo = alloc<VkMemoryAllocateInfo> {
            sType = VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
            allocateInfo()
        }
        val memory = alloc<VkDeviceMemoryVar>()
        vkAllocateMemory!!(handle, memoryAllocateInfo.ptr, null, memory.ptr)
            .checkResult("Failed to allocate memory")
        return DeviceMemory(handle, memory.value!!, memoryAllocateInfo.allocationSize)
    }

    /**
     * Create a new buffer.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateBuffer.html">vkCreateBuffer Manual Page</a>
     */
    fun createBuffer(createInfo: VkBufferCreateInfo.() -> Unit): Buffer = memScoped {
        val bufferCreateInfo = alloc<VkBufferCreateInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
            createInfo()
        }
        val bufferVar = alloc<VkBufferVar>()
        vkCreateBuffer!!(handle, bufferCreateInfo.ptr, null, bufferVar.ptr)
            .checkResult("Failed to create a buffer")
        return Buffer(handle, bufferVar.value!!, bufferCreateInfo.size)
    }

    /**
     * Create a new buffer view.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateBufferView.html">vkCreateBufferView Manual Page</a>
     */
    fun createBufferView(createInfo: VkBufferViewCreateInfo.() -> Unit): BufferView = memScoped {
        val bufferViewCreateInfo = alloc<VkBufferViewCreateInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO
            createInfo()
        }
        val bufferViewVar = alloc<VkBufferViewVar>()
        vkCreateBufferView!!(handle, bufferViewCreateInfo.ptr, null, bufferViewVar.ptr)
            .checkResult("Failed to create buffer view")
        return BufferView(handle, bufferViewVar.value!!)
    }

    /**
     * Create a new command pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateCommandPool.html">vkCreateCommandPool Manual Page</a>
     */
    fun createCommandPool(queueFamilyIndex: UInt, flags: VkCommandPoolCreateFlags = 0u): CommandPool = memScoped {
        val commandPoolCreateInfo = alloc<VkCommandPoolCreateInfo> {
            this.sType = VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO
            this.queueFamilyIndex = queueFamilyIndex
            this.flags = flags
        }
        val commandPoolVar = alloc<VkCommandPoolVar>()
        vkCreateCommandPool!!(handle, commandPoolCreateInfo.ptr, null, commandPoolVar.ptr)
            .checkResult("Failed to create a command pool")
        return CommandPool(handle, commandPoolVar.value!!)
    }

    /**
     * Create a new compute pipeline.
     *
     * @param layout Pipeline layout. Must be `null` when [flags] includes `VK_PIPELINE_CREATE_2_DESCRIPTOR_HEAP_BIT_EXT`.
     * Descriptor heaps require the `VK_EXT_descriptor_heap` extension and its `descriptorHeap` feature to be enabled.
     * @param flags 64-bit pipeline creation flags, passed through [VkPipelineCreateFlags2CreateInfo].
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateComputePipelines.html">vkCreateComputePipelines Manual Page</a>
     */
    fun createComputePipeline(
        layout: PipelineLayout?,
        shaderStage: VkPipelineShaderStageCreateInfo.() -> Unit = {},
        flags: VkPipelineCreateFlags2 = 0uL,
        basePipeline: Pipeline? = null,
        cache: PipelineCache? = null,
    ): Pipeline = memScoped {
        val flagsCreateInfo = alloc<VkPipelineCreateFlags2CreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_CREATE_FLAGS_2_CREATE_INFO
            this.flags = flags
        }
        val computePipelineCreateInfo = alloc<VkComputePipelineCreateInfo> {
            sType = VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO
            pNext = flagsCreateInfo.ptr
            this.layout = layout?.handle
            basePipelineHandle = basePipeline?.handle
            stage.apply {
                sType = VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
                shaderStage()
            }
        }
        val pipelineVar = alloc<VkPipelineVar>()
        vkCreateComputePipelines!!(
            handle,
            cache?.handle,
            1u,
            computePipelineCreateInfo.ptr,
            null,
            pipelineVar.ptr,
        ).checkResult("Failed to create compute pipeline")
        return Pipeline(handle, pipelineVar.value!!)
    }

    /**
     * Create a new descriptor pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDescriptorPool.html">vkCreateDescriptorPool Manual Page</a>
     */
    fun createDescriptorPool(createInfo: VkDescriptorPoolCreateInfo.() -> Unit): DescriptorPool = memScoped {
        val descriptorPoolCreateInfo = alloc<VkDescriptorPoolCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
            createInfo()
        }
        val poolVar = alloc<VkDescriptorPoolVar>()
        vkCreateDescriptorPool!!(handle, descriptorPoolCreateInfo.ptr, null, poolVar.ptr)
            .checkResult("Failed to create descriptor pool")
        return DescriptorPool(handle, poolVar.value!!, descriptorPoolCreateInfo.flags)
    }

    /**
     * Create a new descriptor set layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDescriptorSetLayout.html">vkCreateDescriptorSetLayout Manual Page</a>
     */
    fun createDescriptorSetLayout(createInfo: VkDescriptorSetLayoutCreateInfo.() -> Unit): DescriptorSetLayout = memScoped {
        val descriptorSetLayoutCreateInfo = alloc<VkDescriptorSetLayoutCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO
            createInfo()
        }
        val layoutVar = alloc<VkDescriptorSetLayoutVar>()
        vkCreateDescriptorSetLayout!!(handle, descriptorSetLayoutCreateInfo.ptr, null, layoutVar.ptr)
            .checkResult("Failed to create descriptor set layout")
        return DescriptorSetLayout(handle, layoutVar.value!!)
    }

    /**
     * Create a new event.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateEvent.html">vkCreateEvent Manual Page</a>
     */
    fun createEvent(flags: VkEventCreateFlags = 0u): Event = memScoped {
        val eventInfo = alloc<VkEventCreateInfo> {
            sType = VK_STRUCTURE_TYPE_EVENT_CREATE_INFO
            this.flags = flags
        }
        val eventVar = alloc<VkEventVar>()
        vkCreateEvent!!(handle, eventInfo.ptr, null, eventVar.ptr)
            .checkResult("Failed to create event")
        return Event(handle, eventVar.value!!)
    }

    /**
     * Create a new image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateImage.html">vkCreateImage Manual Page</a>
     */
    fun createImage(createInfo: VkImageCreateInfo.() -> Unit): Image = memScoped {
        val imageCreateInfo = alloc<VkImageCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
            createInfo()
        }
        val imageVar = alloc<VkImageVar>()
        vkCreateImage!!(handle, imageCreateInfo.ptr, null, imageVar.ptr)
            .checkResult("Failed to create an image")
        return Image(handle, imageVar.value!!)
    }

    /**
     * Create an image view from an existing image.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateImageView.html">vkCreateImageView Manual Page</a>
     */
    fun createImageView(createInfo: VkImageViewCreateInfo.() -> Unit): ImageView = memScoped {
        val imageViewCreateInfo = alloc<VkImageViewCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
            createInfo()
        }
        val imageViewVar = alloc<VkImageViewVar>()
        vkCreateImageView!!(handle, imageViewCreateInfo.ptr, null, imageViewVar.ptr)
            .checkResult("Failed to create an image view")
        return ImageView(handle, imageViewVar.value!!)
    }

    /**
     * Create a new fence.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateFence.html">vkCreateFence Manual Page</a>
     */
    fun createFence(createInfo: VkFenceCreateInfo.() -> Unit = {}): Fence = memScoped {
        val fenceInfo = alloc<VkFenceCreateInfo> {
            sType = VK_STRUCTURE_TYPE_FENCE_CREATE_INFO
            createInfo()
        }
        val fenceVar = alloc<VkFenceVar>()
        vkCreateFence!!(handle, fenceInfo.ptr, null, fenceVar.ptr)
            .checkResult("Failed to create a fence")
        return Fence(handle, fenceVar.value!!)
    }

    /**
     * Create a new graphics pipeline.
     *
     * @param layout Pipeline layout. Must be `null` when [flags] includes `VK_PIPELINE_CREATE_2_DESCRIPTOR_HEAP_BIT_EXT`.
     * Descriptor heaps require the `VK_EXT_descriptor_heap` extension and its `descriptorHeap` feature to be enabled.
     * @param flags 64-bit pipeline creation flags, passed through [VkPipelineCreateFlags2CreateInfo].
     * Do not also chain this structure through [renderingCreateInfo].
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateGraphicsPipelines.html">vkCreateGraphicsPipelines Manual Page</a>
     */
    fun createGraphicsPipeline(
        layout: PipelineLayout?,
        stageCount: UInt,
        stages: VkPipelineShaderStageCreateInfo.(UInt) -> Unit = {},
        vertexInputState: VkPipelineVertexInputStateCreateInfo.() -> Unit = {},
        inputAssemblyState: VkPipelineInputAssemblyStateCreateInfo.() -> Unit = {},
        tessellationState: VkPipelineTessellationStateCreateInfo.() -> Unit = {},
        viewportState: VkPipelineViewportStateCreateInfo.() -> Unit = {},
        rasterizationState: VkPipelineRasterizationStateCreateInfo.() -> Unit = {},
        multisampleState: VkPipelineMultisampleStateCreateInfo.() -> Unit = {},
        depthStencilState: VkPipelineDepthStencilStateCreateInfo.() -> Unit = {},
        colorBlendState: VkPipelineColorBlendStateCreateInfo.() -> Unit = {},
        dynamicState: VkPipelineDynamicStateCreateInfo.() -> Unit = {},
        renderingCreateInfo: VkPipelineRenderingCreateInfo.() -> Unit = {},
        flags: VkPipelineCreateFlags2 = 0uL,
        basePipeline: Pipeline? = null,
        cache: PipelineCache? = null,
    ): Pipeline = memScoped {
        val shaderStageCreateInfo = if (stageCount > 0u) {
            allocArray<VkPipelineShaderStageCreateInfo>(stageCount.toLong()) {
                sType = VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
                stages(it.toUInt())
            }
        } else {
            null
        }
        val vertexInputStateCreateInfo = alloc<VkPipelineVertexInputStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO
            vertexInputState()
        }
        val inputAssemblyStateCreateInfo = alloc<VkPipelineInputAssemblyStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO
            inputAssemblyState()
        }
        val tessellationStateCreateInfo = alloc<VkPipelineTessellationStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_TESSELLATION_STATE_CREATE_INFO
            tessellationState()
        }
        val viewportStateCreateInfo = alloc<VkPipelineViewportStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO
            viewportState()
        }
        val rasterizationStateCreateInfo = alloc<VkPipelineRasterizationStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO
            rasterizationState()
        }
        val multisampleStateCreateInfo = alloc<VkPipelineMultisampleStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO
            multisampleState()
        }
        val depthStencilStateCreateInfo = alloc<VkPipelineDepthStencilStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO
            depthStencilState()
        }
        val colorBlendStateCreateInfo = alloc<VkPipelineColorBlendStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
            colorBlendState()
        }
        val dynamicStateCreateInfo = alloc<VkPipelineDynamicStateCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO
            dynamicState()
        }
        val renderingCreateInfo = alloc<VkPipelineRenderingCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_RENDERING_CREATE_INFO
            renderingCreateInfo()
        }
        val flagsCreateInfo = alloc<VkPipelineCreateFlags2CreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_CREATE_FLAGS_2_CREATE_INFO
            pNext = renderingCreateInfo.ptr
            this.flags = flags
        }
        val graphicsPipelineCreateInfo = alloc<VkGraphicsPipelineCreateInfo> {
            sType = VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
            this.stageCount = stageCount
            this.layout = layout?.handle
            pStages = shaderStageCreateInfo
            pVertexInputState = vertexInputStateCreateInfo.ptr
            pInputAssemblyState = inputAssemblyStateCreateInfo.ptr
            pTessellationState = tessellationStateCreateInfo.ptr
            pViewportState = viewportStateCreateInfo.ptr
            pRasterizationState = rasterizationStateCreateInfo.ptr
            pMultisampleState = multisampleStateCreateInfo.ptr
            pDepthStencilState = depthStencilStateCreateInfo.ptr
            pColorBlendState = colorBlendStateCreateInfo.ptr
            pDynamicState = dynamicStateCreateInfo.ptr
            pNext = flagsCreateInfo.ptr
            basePipelineHandle = basePipeline?.handle
        }
        val pipelineVar = alloc<VkPipelineVar>()
        vkCreateGraphicsPipelines!!(
            handle,
            cache?.handle,
            1u,
            graphicsPipelineCreateInfo.ptr,
            null,
            pipelineVar.ptr,
        ).checkResult("Failed to create graphics pipeline")
        return Pipeline(handle, pipelineVar.value!!)
    }

    /**
     * Create a new pipeline cache.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreatePipelineCache.html">vkCreatePipelineCache Manual Page</a>
     */
    fun createPipelineCache(createInfo: VkPipelineCacheCreateInfo.() -> Unit = {}): PipelineCache = memScoped {
        val pipelineCacheCreateInfo = alloc<VkPipelineCacheCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO
            createInfo()
        }
        val pipelineCacheVar = alloc<VkPipelineCacheVar>()
        vkCreatePipelineCache!!(handle, pipelineCacheCreateInfo.ptr, null, pipelineCacheVar.ptr)
            .checkResult("Failed to create pipeline cache")
        return PipelineCache(handle, pipelineCacheVar.value!!)
    }

    /**
     * Create a new pipeline layout.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreatePipelineLayout.html">vkCreatePipelineLayout Manual Page</a>
     */
    fun createPipelineLayout(createInfo: VkPipelineLayoutCreateInfo.() -> Unit = {}): PipelineLayout = memScoped {
        val pipelineLayoutCreateInfo = alloc<VkPipelineLayoutCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO
            createInfo()
        }
        val pipelineLayoutVar = alloc<VkPipelineLayoutVar>()
        vkCreatePipelineLayout!!(handle, pipelineLayoutCreateInfo.ptr, null, pipelineLayoutVar.ptr)
            .checkResult("Failed to create pipeline layout")
        return PipelineLayout(handle, pipelineLayoutVar.value!!)
    }

    /**
     * Create a new query pool.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateQueryPool.html">vkCreateQueryPool Manual Page</a>
     */
    fun createQueryPool(createInfo: VkQueryPoolCreateInfo.() -> Unit): QueryPool = memScoped {
        val queryPoolCreateInfo = alloc<VkQueryPoolCreateInfo> {
            sType = VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO
            createInfo()
        }
        val queryPoolVar = alloc<VkQueryPoolVar>()
        vkCreateQueryPool!!(handle, queryPoolCreateInfo.ptr, null, queryPoolVar.ptr)
            .checkResult("Failed to create query pool")
        return QueryPool(handle, queryPoolVar.value!!)
    }

    /**
     * Create a new sampler.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateSampler.html">vkCreateSampler Manual Page</a>
     */
    fun createSampler(createInfo: VkSamplerCreateInfo.() -> Unit): Sampler = memScoped {
        val samplerCreateInfo = alloc<VkSamplerCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
            createInfo()
        }
        val samplerVar = alloc<VkSamplerVar>()
        vkCreateSampler!!(handle, samplerCreateInfo.ptr, null, samplerVar.ptr)
            .checkResult("Failed to create sampler")
        return Sampler(handle, samplerVar.value!!)
    }

    /**
     * Create a new semaphore.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateSemaphore.html">vkCreateSemaphore Manual Page</a>
     */
    fun createSemaphore(semaphoreType: VkSemaphoreType = VK_SEMAPHORE_TYPE_BINARY, initialValue: ULong = 0uL): Semaphore = memScoped {
        assert(semaphoreType == VK_SEMAPHORE_TYPE_BINARY || semaphoreType == VK_SEMAPHORE_TYPE_TIMELINE) {
            "semaphoreType must be a valid VkSemaphoreType value"
        }
        assert(semaphoreType != VK_SEMAPHORE_TYPE_BINARY || initialValue == 0uL) {
            "If semaphoreType is VK_SEMAPHORE_TYPE_BINARY, initialValue must be zero"
        }
        val typeInfo = alloc<VkSemaphoreTypeCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SEMAPHORE_TYPE_CREATE_INFO
            this.semaphoreType = semaphoreType
            this.initialValue = initialValue
        }
        val semaphoreInfo = alloc<VkSemaphoreCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO
            pNext = typeInfo.ptr
        }
        val semaphore = alloc<VkSemaphoreVar>()
        vkCreateSemaphore!!(handle, semaphoreInfo.ptr, null, semaphore.ptr)
            .checkResult("Failed to create a semaphore")
        return Semaphore(device = handle, handle = semaphore.value!!, semaphoreType)
    }

    /**
     * Create a new shader module.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateShaderModule.html">vkCreateShaderModule Manual Page</a>
     */
    fun createShaderModule(createInfo: VkShaderModuleCreateInfo.() -> Unit): ShaderModule = memScoped {
        val shaderModuleCreateInfo = alloc<VkShaderModuleCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO
            createInfo()
        }
        val shaderModule = alloc<VkShaderModuleVar>()
        vkCreateShaderModule!!(handle, shaderModuleCreateInfo.ptr, null, shaderModule.ptr)
            .checkResult("Failed to create shader module")
        return ShaderModule(handle, shaderModule.value!!)
    }

    /**
     * Create a swapchain.
     * Requires `VK_KHR_swapchain` to be enabled on the device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateSwapchainKHR.html">vkCreateSwapchainKHR Manual Page</a>
     */
    fun createSwapchain(createInfo: VkSwapchainCreateInfoKHR.() -> Unit): Swapchain = memScoped {
        assert(VK_KHR_SWAPCHAIN_EXTENSION_NAME in enabledExtensions) {
            "Creating a swapchain requires VK_KHR_swapchain"
        }
        val swapChainCreateInfo = alloc<VkSwapchainCreateInfoKHR> {
            sType = VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR
            createInfo()
        }
        val swapChainVar = alloc<VkSwapchainKHRVar>()
        vkCreateSwapchainKHR!!(handle, swapChainCreateInfo.ptr, null, swapChainVar.ptr)
            .checkResult("Failed to create a swap chain")
        return Swapchain(handle, swapChainVar.value!!)
    }

    /**
     * Query whether a descriptor set layout can be created.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetDescriptorSetLayoutSupport.html">vkGetDescriptorSetLayoutSupport Manual Page</a>
     */
    fun getDescriptorSetLayoutSupport(createInfo: VkDescriptorSetLayoutCreateInfo): Boolean = memScoped {
        val layoutSupport = alloc<VkDescriptorSetLayoutSupport> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_SUPPORT
        }
        vkGetDescriptorSetLayoutSupport!!(handle, createInfo.ptr, layoutSupport.ptr)
        return layoutSupport.supported == VK_TRUE
    }

    /**
     * Retrieve information about an image subresource.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetImageSubresourceLayout2.html">vkGetImageSubresourceLayout2 Manual Page</a>
     */
    fun getImageSubresourceLayout(
        image: Image,
        aspectMask: VkImageAspectFlags,
        mipLevel: UInt = 0u,
        arrayLayer: UInt = 0u,
    ): ImageSubresourceLayout = memScoped {
        assert(aspectMask != 0u) { "aspectMask must not be 0" }
        val subresource = alloc<VkImageSubresource2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_SUBRESOURCE_2
            imageSubresource.aspectMask = aspectMask
            imageSubresource.mipLevel = mipLevel
            imageSubresource.arrayLayer = arrayLayer
        }
        val hostMemcpySize = alloc<VkSubresourceHostMemcpySize> {
            sType = VK_STRUCTURE_TYPE_SUBRESOURCE_HOST_MEMCPY_SIZE
        }
        val subresourceLayout = alloc<VkSubresourceLayout2> {
            sType = VK_STRUCTURE_TYPE_SUBRESOURCE_LAYOUT_2
            pNext = hostMemcpySize.ptr
        }
        vkGetImageSubresourceLayout2!!(handle, image.handle, subresource.ptr, subresourceLayout.ptr)
        return subresourceLayout.subresourceLayout.toImageSubresourceLayout(hostMemcpySize.size)
    }

    /**
     * Get a queue from the device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetDeviceQueue.html">vkGetDeviceQueue Manual Page</a>
     */
    fun getQueue(queueFamilyIndex: UInt, queueIndex: UInt = 0u): Queue = memScoped {
        val queueVar = alloc<VkQueueVar>()
        vkGetDeviceQueue!!(handle, queueFamilyIndex, queueIndex, queueVar.ptr)
        return Queue(queueVar.value!!, queueFamilyIndex)
    }

    /**
     * Update descriptor sets.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkUpdateDescriptorSets.html">vkUpdateDescriptorSets Manual Page</a>
     */
    fun updateDescriptorSets(writes: List<VkWriteDescriptorSet>, copies: List<VkCopyDescriptorSet> = emptyList()): Unit = memScoped {
        val writesArray = if (writes.isNotEmpty()) {
            allocArray<VkWriteDescriptorSet>(writes.size) { index ->
                writes[index]
            }
        } else {
            null
        }

        val copiesArray = if (copies.isNotEmpty()) {
            allocArray<VkCopyDescriptorSet>(copies.size) { index ->
                copies[index]
            }
        } else {
            null
        }

        vkUpdateDescriptorSets!!(
            handle,
            writes.size.toUInt(),
            writesArray,
            copies.size.toUInt(),
            copiesArray,
        )
    }

    /**
     * Wait for the device to become idle.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDeviceWaitIdle.html">vkDeviceWaitIdle Manual Page</a>
     */
    fun waitIdle() {
        vkDeviceWaitIdle!!(handle).checkResult("Failed to wait for device idle")
    }

    /**
     * Destroy the logical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDevice.html">vkDestroyDevice Manual Page</a>
     */
    override fun close() {
        vkDestroyDevice!!(handle, null)
    }
}
