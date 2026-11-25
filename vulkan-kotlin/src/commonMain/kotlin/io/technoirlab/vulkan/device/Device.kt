package io.technoirlab.vulkan.device

import io.technoirlab.volk.VK_FORMAT_UNDEFINED
import io.technoirlab.volk.VK_IMAGE_ASPECT_NONE
import io.technoirlab.volk.VK_IMAGE_CREATE_DISJOINT_BIT
import io.technoirlab.volk.VK_KHR_SWAPCHAIN_EXTENSION_NAME
import io.technoirlab.volk.VK_OBJECT_TYPE_DEVICE
import io.technoirlab.volk.VK_QUERY_TYPE_PIPELINE_STATISTICS
import io.technoirlab.volk.VK_QUERY_TYPE_RESULT_STATUS_ONLY_KHR
import io.technoirlab.volk.VK_QUERY_TYPE_TRANSFORM_FEEDBACK_STREAM_EXT
import io.technoirlab.volk.VK_SEMAPHORE_TYPE_BINARY
import io.technoirlab.volk.VK_SEMAPHORE_TYPE_TIMELINE
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_BUFFER_VIEW_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_SUPPORT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_BUFFER_MEMORY_REQUIREMENTS
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_IMAGE_MEMORY_REQUIREMENTS
import io.technoirlab.volk.VK_STRUCTURE_TYPE_DEVICE_IMAGE_SUBRESOURCE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_EVENT_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_SUBRESOURCE_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_DEDICATED_REQUIREMENTS
import io.technoirlab.volk.VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_CREATE_FLAGS_2_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_CREATION_FEEDBACK_CREATE_INFO
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
import io.technoirlab.volk.VK_STRUCTURE_TYPE_RENDERING_AREA_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO
import io.technoirlab.volk.VK_STRUCTURE_TYPE_SEMAPHORE_TYPE_CREATE_INFO
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
import io.technoirlab.volk.VkDeviceBufferMemoryRequirements
import io.technoirlab.volk.VkDeviceImageMemoryRequirements
import io.technoirlab.volk.VkDeviceImageSubresourceInfo
import io.technoirlab.volk.VkDeviceMemoryVar
import io.technoirlab.volk.VkEventCreateFlags
import io.technoirlab.volk.VkEventCreateInfo
import io.technoirlab.volk.VkEventVar
import io.technoirlab.volk.VkExtent2D
import io.technoirlab.volk.VkFenceCreateInfo
import io.technoirlab.volk.VkFenceVar
import io.technoirlab.volk.VkFormat
import io.technoirlab.volk.VkGraphicsPipelineCreateInfo
import io.technoirlab.volk.VkImageAspectFlagBits
import io.technoirlab.volk.VkImageAspectFlags
import io.technoirlab.volk.VkImageCreateInfo
import io.technoirlab.volk.VkImageSubresource2
import io.technoirlab.volk.VkImageVar
import io.technoirlab.volk.VkImageViewCreateInfo
import io.technoirlab.volk.VkImageViewVar
import io.technoirlab.volk.VkMemoryAllocateInfo
import io.technoirlab.volk.VkMemoryDedicatedRequirements
import io.technoirlab.volk.VkMemoryRequirements2
import io.technoirlab.volk.VkObjectType
import io.technoirlab.volk.VkPipelineCacheCreateInfo
import io.technoirlab.volk.VkPipelineCacheVar
import io.technoirlab.volk.VkPipelineColorBlendStateCreateInfo
import io.technoirlab.volk.VkPipelineCreateFlags2
import io.technoirlab.volk.VkPipelineCreateFlags2CreateInfo
import io.technoirlab.volk.VkPipelineCreationFeedbackCreateInfo
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
import io.technoirlab.volk.VkRenderingAreaInfo
import io.technoirlab.volk.VkSamplerCreateInfo
import io.technoirlab.volk.VkSamplerVar
import io.technoirlab.volk.VkSemaphoreCreateInfo
import io.technoirlab.volk.VkSemaphoreType
import io.technoirlab.volk.VkSemaphoreTypeCreateInfo
import io.technoirlab.volk.VkSemaphoreVar
import io.technoirlab.volk.VkShaderModuleCreateFlags
import io.technoirlab.volk.VkShaderModuleVar
import io.technoirlab.volk.VkSubresourceHostMemcpySize
import io.technoirlab.volk.VkSubresourceLayout2
import io.technoirlab.volk.VkSwapchainCreateInfoKHR
import io.technoirlab.volk.VkSwapchainKHRVar
import io.technoirlab.volk.VkWriteDescriptorSet
import io.technoirlab.volk.VolkDeviceTable
import io.technoirlab.volk.VolkInstanceTable
import io.technoirlab.vulkan.Extent2D
import io.technoirlab.vulkan.Instance
import io.technoirlab.vulkan.VulkanObject
import io.technoirlab.vulkan.buffer.Buffer
import io.technoirlab.vulkan.buffer.BufferView
import io.technoirlab.vulkan.checkResult
import io.technoirlab.vulkan.command.CommandPool
import io.technoirlab.vulkan.descriptor.DescriptorPool
import io.technoirlab.vulkan.descriptor.DescriptorSetLayout
import io.technoirlab.vulkan.image.Image
import io.technoirlab.vulkan.image.ImageSubresourceLayout
import io.technoirlab.vulkan.image.ImageView
import io.technoirlab.vulkan.image.Sampler
import io.technoirlab.vulkan.image.toImageSubresourceLayout
import io.technoirlab.vulkan.internal.DispatchTable
import io.technoirlab.vulkan.internal.containsStructure
import io.technoirlab.vulkan.memory.DeviceMemory
import io.technoirlab.vulkan.memory.MemoryRegion
import io.technoirlab.vulkan.memory.MemoryRequirements
import io.technoirlab.vulkan.memory.toMemoryRequirements
import io.technoirlab.vulkan.pipeline.Pipeline
import io.technoirlab.vulkan.pipeline.PipelineCache
import io.technoirlab.vulkan.pipeline.PipelineCreationResult
import io.technoirlab.vulkan.pipeline.PipelineLayout
import io.technoirlab.vulkan.pipeline.allocPipelineCreationFeedbackCreateInfo
import io.technoirlab.vulkan.pipeline.toPipelineCreationResult
import io.technoirlab.vulkan.pipeline.toPipelineOrNull
import io.technoirlab.vulkan.presentation.Swapchain
import io.technoirlab.vulkan.query.QueryPool
import io.technoirlab.vulkan.shader.ShaderModule
import io.technoirlab.vulkan.shader.shaderModuleCreateInfo
import io.technoirlab.vulkan.sync.Event
import io.technoirlab.vulkan.sync.Fence
import io.technoirlab.vulkan.sync.Semaphore
import io.technoirlab.vulkan.toExtent2D
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.UIntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readValue
import kotlinx.cinterop.value
import kotlin.assert

/**
 * Wrapper for [VkDevice].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkDevice.html">VkDevice Manual Page</a>
 */
class Device internal constructor(
    instance: Instance,
    override val handle: VkDevice,
    val enabledExtensions: Set<String>,
    private val dispatchTable: DispatchTable<VolkDeviceTable>,
) : VulkanObject,
    AutoCloseable {
    private val dispatchLoader = instance.dispatchLoader

    // Dropped on close, so that instance extension commands on a closed device fail like its own commands.
    private var instance: Instance? = instance

    /**
     * The table holding the commands of instance extensions that take this device or one of its queues and command
     * buffers, such as those of `VK_EXT_debug_utils`. volk resolves them with the instance, not with the device.
     */
    internal val instanceDispatch: VolkInstanceTable
        get() = instance!!.dispatch

    /**
     * The device-level commands resolved for this device, including those of its queues and command buffers.
     *
     * Use it to call commands that this library does not wrap. Commands of instance extensions, such as
     * `VK_EXT_debug_utils`, are in [Instance.dispatch] even when they take a device, queue or command buffer. The
     * table belongs to the device: closing the device clears it, and it must not be used once the device object is
     * unreachable.
     */
    val dispatch = dispatchTable.commands

    /**
     * @inheritDoc
     */
    override val type: VkObjectType get() = VK_OBJECT_TYPE_DEVICE

    /**
     * Allocate device memory.
     *
     * Protected memory types require the `protectedMemory` feature to be enabled on the device.
     * `VK_MEMORY_ALLOCATE_DEVICE_ADDRESS_BIT` and `VK_MEMORY_ALLOCATE_DEVICE_ADDRESS_CAPTURE_REPLAY_BIT`
     * in a chained `VkMemoryAllocateFlagsInfo` require `bufferDeviceAddress` and
     * `bufferDeviceAddressCaptureReplay`, respectively.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkAllocateMemory.html">vkAllocateMemory Manual Page</a>
     */
    fun allocateMemory(allocateInfo: VkMemoryAllocateInfo.() -> Unit): DeviceMemory = memScoped {
        val memoryAllocateInfo = alloc<VkMemoryAllocateInfo> {
            sType = VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO
            allocateInfo()
        }
        val memory = alloc<VkDeviceMemoryVar>()
        dispatch.vkAllocateMemory!!(handle, memoryAllocateInfo.ptr, null, memory.ptr)
            .checkResult("Failed to allocate memory")
        return DeviceMemory(this@Device, memory.value!!, memoryAllocateInfo.allocationSize)
    }

    /**
     * Create a new buffer.
     *
     * Sparse binding, residency, and aliasing flags require the `sparseBinding`, `sparseResidencyBuffer`, and
     * `sparseResidencyAliased` features, respectively, to be enabled on the device. Protected buffers require
     * `protectedMemory`; `VK_BUFFER_CREATE_DEVICE_ADDRESS_CAPTURE_REPLAY_BIT` requires
     * `bufferDeviceAddressCaptureReplay`.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateBuffer.html">vkCreateBuffer Manual Page</a>
     */
    fun createBuffer(createInfo: VkBufferCreateInfo.() -> Unit): Buffer = memScoped {
        val bufferCreateInfo = alloc<VkBufferCreateInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
            createInfo()
        }
        val bufferVar = alloc<VkBufferVar>()
        dispatch.vkCreateBuffer!!(handle, bufferCreateInfo.ptr, null, bufferVar.ptr)
            .checkResult("Failed to create a buffer")
        return Buffer(this@Device, bufferVar.value!!, bufferCreateInfo.size)
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
        dispatch.vkCreateBufferView!!(handle, bufferViewCreateInfo.ptr, null, bufferViewVar.ptr)
            .checkResult("Failed to create buffer view")
        return BufferView(this@Device, bufferViewVar.value!!)
    }

    /**
     * Create a new command pool.
     *
     * Requires the `protectedMemory` feature to be enabled on the device when [flags] includes
     * `VK_COMMAND_POOL_CREATE_PROTECTED_BIT`.
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
        dispatch.vkCreateCommandPool!!(handle, commandPoolCreateInfo.ptr, null, commandPoolVar.ptr)
            .checkResult("Failed to create a command pool")
        return CommandPool(this@Device, commandPoolVar.value!!)
    }

    /**
     * Create a new compute pipeline.
     *
     * Pipeline flags that fail on compilation or return early on failure require `pipelineCreationCacheControl`.
     * Protected-access restriction flags require `pipelineProtectedAccess`. These features must be enabled on the device.
     * Shader stage flags for varying subgroup sizes require `subgroupSizeControl`; requiring full subgroups
     * requires `computeFullSubgroups`. Explicit subgroup sizes also require `subgroupSizeControl`.
     *
     * Use [createComputePipelineWithFeedback] to also receive the implementation's feedback about the creation.
     *
     * @param layout Pipeline layout. Must be `null` when [flags] includes `VK_PIPELINE_CREATE_2_DESCRIPTOR_HEAP_BIT_EXT`.
     * Descriptor heaps require the `VK_EXT_descriptor_heap` extension and its `descriptorHeap` feature to be enabled.
     * @param flags 64-bit pipeline creation flags, passed through [VkPipelineCreateFlags2CreateInfo].
     * @return The created pipeline, or `null` if [flags] includes
     * `VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT` and the implementation reported
     * `VK_PIPELINE_COMPILE_REQUIRED` because it would have had to compile the pipeline.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateComputePipelines.html">vkCreateComputePipelines Manual Page</a>
     */
    fun createComputePipeline(
        layout: PipelineLayout?,
        shaderStage: VkPipelineShaderStageCreateInfo.() -> Unit = {},
        flags: VkPipelineCreateFlags2 = 0uL,
        basePipeline: Pipeline? = null,
        cache: PipelineCache? = null,
    ): Pipeline? = createComputePipelineImpl(layout, shaderStage, flags, basePipeline, cache, creationFeedbackCreateInfo = null)

    /**
     * Create a new compute pipeline and report the implementation's feedback about its creation.
     *
     * Takes the same parameters as [createComputePipeline], with the same feature requirements and the same
     * behavior on failure. It additionally chains [VkPipelineCreationFeedbackCreateInfo] to the pipeline creation
     * parameters, and copies the feedback out of temporary native memory before returning.
     *
     * Feedback is advisory. An implementation may report none for the pipeline or for its stage, which is not
     * a failure.
     *
     * @return The created pipeline, which the caller owns and must close, with the feedback about the pipeline
     * and about its compute stage. The pipeline is `null`, and the feedback is still reported, if [flags] includes
     * `VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT` and the implementation reported
     * `VK_PIPELINE_COMPILE_REQUIRED`.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateComputePipelines.html">vkCreateComputePipelines Manual Page</a>
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineCreationFeedbackCreateInfo.html">VkPipelineCreationFeedbackCreateInfo Manual Page</a>
     */
    fun createComputePipelineWithFeedback(
        layout: PipelineLayout?,
        shaderStage: VkPipelineShaderStageCreateInfo.() -> Unit = {},
        flags: VkPipelineCreateFlags2 = 0uL,
        basePipeline: Pipeline? = null,
        cache: PipelineCache? = null,
    ): PipelineCreationResult = memScoped {
        val creationFeedbackCreateInfo = allocPipelineCreationFeedbackCreateInfo(stageCount = 1u)
        val pipeline = createComputePipelineImpl(layout, shaderStage, flags, basePipeline, cache, creationFeedbackCreateInfo)
        return creationFeedbackCreateInfo.toPipelineCreationResult(pipeline)
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
        dispatch.vkCreateDescriptorPool!!(handle, descriptorPoolCreateInfo.ptr, null, poolVar.ptr)
            .checkResult("Failed to create descriptor pool")
        return DescriptorPool(this@Device, poolVar.value!!, descriptorPoolCreateInfo.flags)
    }

    /**
     * Create a new descriptor set layout.
     *
     * Push descriptor layouts require the `pushDescriptor` feature to be enabled on the device.
     * Inline uniform block bindings require `inlineUniformBlock`. In a chained
     * `VkDescriptorSetLayoutBindingFlagsCreateInfo`, partially bound bindings require `descriptorBindingPartiallyBound`,
     * variable descriptor counts require `descriptorBindingVariableDescriptorCount`, and updating unused pending
     * bindings requires `descriptorBindingUpdateUnusedWhilePending`. Update-after-bind bindings require the
     * corresponding `descriptorBinding*UpdateAfterBind` feature for their descriptor type.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateDescriptorSetLayout.html">vkCreateDescriptorSetLayout Manual Page</a>
     */
    fun createDescriptorSetLayout(createInfo: VkDescriptorSetLayoutCreateInfo.() -> Unit): DescriptorSetLayout = memScoped {
        val descriptorSetLayoutCreateInfo = alloc<VkDescriptorSetLayoutCreateInfo> {
            sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO
            createInfo()
        }
        val layoutVar = alloc<VkDescriptorSetLayoutVar>()
        dispatch.vkCreateDescriptorSetLayout!!(handle, descriptorSetLayoutCreateInfo.ptr, null, layoutVar.ptr)
            .checkResult("Failed to create descriptor set layout")
        return DescriptorSetLayout(this@Device, layoutVar.value!!)
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
        dispatch.vkCreateEvent!!(handle, eventInfo.ptr, null, eventVar.ptr)
            .checkResult("Failed to create event")
        return Event(this@Device, eventVar.value!!)
    }

    /**
     * Create a new image.
     *
     * `VK_IMAGE_USAGE_HOST_TRANSFER_BIT` requires the `hostImageCopy` feature to be enabled on the device.
     * Multisampled storage images require `shaderStorageImageMultisample`; protected images require `protectedMemory`.
     * Sparse binding and aliasing flags require `sparseBinding` and `sparseResidencyAliased`, respectively.
     * Sparse residency requires `sparseResidencyImage2D` or `sparseResidencyImage3D` for the image type, plus the
     * matching `sparseResidency2Samples`, `sparseResidency4Samples`, `sparseResidency8Samples`, or
     * `sparseResidency16Samples` feature for multisampled images.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateImage.html">vkCreateImage Manual Page</a>
     */
    fun createImage(createInfo: VkImageCreateInfo.() -> Unit): Image = memScoped {
        val imageCreateInfo = alloc<VkImageCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
            createInfo()
        }
        val imageVar = alloc<VkImageVar>()
        dispatch.vkCreateImage!!(handle, imageCreateInfo.ptr, null, imageVar.ptr)
            .checkResult("Failed to create an image")
        return Image(this@Device, imageVar.value!!)
    }

    /**
     * Create an image view from an existing image.
     *
     * Requires the `imageCubeArray` feature to be enabled on the device when `viewType` is `VK_IMAGE_VIEW_TYPE_CUBE_ARRAY`.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateImageView.html">vkCreateImageView Manual Page</a>
     */
    fun createImageView(createInfo: VkImageViewCreateInfo.() -> Unit): ImageView = memScoped {
        val imageViewCreateInfo = alloc<VkImageViewCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO
            createInfo()
        }
        val imageViewVar = alloc<VkImageViewVar>()
        dispatch.vkCreateImageView!!(handle, imageViewCreateInfo.ptr, null, imageViewVar.ptr)
            .checkResult("Failed to create an image view")
        return ImageView(this@Device, imageViewVar.value!!)
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
        dispatch.vkCreateFence!!(handle, fenceInfo.ptr, null, fenceVar.ptr)
            .checkResult("Failed to create a fence")
        return Fence(this@Device, fenceVar.value!!)
    }

    /**
     * Create a new graphics pipeline.
     *
     * Pipeline flags that fail on compilation or return early on failure require `pipelineCreationCacheControl`.
     * Protected-access restriction flags require `pipelineProtectedAccess`. These features must be enabled on the device.
     * Shader stage flags for varying subgroup sizes require `subgroupSizeControl`; requiring full subgroups
     * requires `computeFullSubgroups`. Explicit subgroup sizes also require `subgroupSizeControl`.
     *
     * Optional pipeline state requires the corresponding features to be enabled: `geometryShader` and
     * `tessellationShader` for those shader stages, `depthClamp` for depth clamping, `depthBiasClamp` for nonzero
     * bias clamping, `fillModeNonSolid` for line or point polygon modes, and `wideLines` for line widths other than one.
     * Sample shading requires `sampleRateShading`; alpha-to-one requires `alphaToOne`; depth bounds testing
     * requires `depthBounds`. Multiple viewports or scissors require `multiViewport`. Distinct per-attachment
     * blend states require `independentBlend`, `SRC1` blend factors require `dualSrcBlend`, and logical pixel
     * operations require `logicOp`. Non-default line rasterization modes and line stippling require the matching
     * `rectangularLines`, `bresenhamLines`, `smoothLines`, and `stippled*Lines` features.
     *
     * Use [createGraphicsPipelineWithFeedback] to also receive the implementation's feedback about the creation.
     *
     * @param layout Pipeline layout. Must be `null` when [flags] includes `VK_PIPELINE_CREATE_2_DESCRIPTOR_HEAP_BIT_EXT`.
     * Descriptor heaps require the `VK_EXT_descriptor_heap` extension and its `descriptorHeap` feature to be enabled.
     * @param flags 64-bit pipeline creation flags, passed through [VkPipelineCreateFlags2CreateInfo].
     * Do not also chain this structure through [renderingCreateInfo].
     * @return The created pipeline, or `null` if [flags] includes
     * `VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT` and the implementation reported
     * `VK_PIPELINE_COMPILE_REQUIRED` because it would have had to compile the pipeline.
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
    ): Pipeline? = createGraphicsPipelineImpl(
        layout,
        stageCount,
        stages,
        vertexInputState,
        inputAssemblyState,
        tessellationState,
        viewportState,
        rasterizationState,
        multisampleState,
        depthStencilState,
        colorBlendState,
        dynamicState,
        renderingCreateInfo,
        flags,
        basePipeline,
        cache,
        creationFeedbackCreateInfo = null,
    )

    /**
     * Create a new graphics pipeline and report the implementation's feedback about its creation.
     *
     * Takes the same parameters as [createGraphicsPipeline], with the same feature requirements and the same
     * behavior on failure. It additionally chains [VkPipelineCreationFeedbackCreateInfo] to the pipeline creation
     * parameters, and copies the feedback out of temporary native memory before returning.
     *
     * Feedback is advisory. An implementation may report none for the pipeline or for any of its stages, which
     * is not a failure.
     *
     * @param renderingCreateInfo Configures dynamic rendering. Structures chained through it are preserved, but
     * it must not chain [VkPipelineCreationFeedbackCreateInfo].
     * @return The created pipeline, which the caller owns and must close, with the feedback about the pipeline
     * and about each of its [stageCount] stages in the order [stages] configures them. The pipeline is `null`,
     * and the feedback is still reported, if [flags] includes
     * `VK_PIPELINE_CREATE_2_FAIL_ON_PIPELINE_COMPILE_REQUIRED_BIT` and the implementation reported
     * `VK_PIPELINE_COMPILE_REQUIRED`.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateGraphicsPipelines.html">vkCreateGraphicsPipelines Manual Page</a>
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/VkPipelineCreationFeedbackCreateInfo.html">VkPipelineCreationFeedbackCreateInfo Manual Page</a>
     */
    fun createGraphicsPipelineWithFeedback(
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
    ): PipelineCreationResult = memScoped {
        val creationFeedbackCreateInfo = allocPipelineCreationFeedbackCreateInfo(stageCount)
        val pipeline = createGraphicsPipelineImpl(
            layout,
            stageCount,
            stages,
            vertexInputState,
            inputAssemblyState,
            tessellationState,
            viewportState,
            rasterizationState,
            multisampleState,
            depthStencilState,
            colorBlendState,
            dynamicState,
            renderingCreateInfo,
            flags,
            basePipeline,
            cache,
            creationFeedbackCreateInfo,
        )
        return creationFeedbackCreateInfo.toPipelineCreationResult(pipeline)
    }

    /**
     * Create a new pipeline cache.
     *
     * Requires the `pipelineCreationCacheControl` feature to be enabled on the device when `flags` includes
     * `VK_PIPELINE_CACHE_CREATE_EXTERNALLY_SYNCHRONIZED_BIT`.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreatePipelineCache.html">vkCreatePipelineCache Manual Page</a>
     */
    fun createPipelineCache(createInfo: VkPipelineCacheCreateInfo.() -> Unit = {}): PipelineCache = memScoped {
        val pipelineCacheCreateInfo = alloc<VkPipelineCacheCreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_CACHE_CREATE_INFO
            createInfo()
        }
        val pipelineCacheVar = alloc<VkPipelineCacheVar>()
        dispatch.vkCreatePipelineCache!!(handle, pipelineCacheCreateInfo.ptr, null, pipelineCacheVar.ptr)
            .checkResult("Failed to create pipeline cache")
        return PipelineCache(this@Device, pipelineCacheVar.value!!)
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
        dispatch.vkCreatePipelineLayout!!(handle, pipelineLayoutCreateInfo.ptr, null, pipelineLayoutVar.ptr)
            .checkResult("Failed to create pipeline layout")
        return PipelineLayout(this@Device, pipelineLayoutVar.value!!)
    }

    /**
     * Create a new query pool.
     *
     * Requires the `pipelineStatisticsQuery` feature to be enabled on the device when `queryType` is
     * `VK_QUERY_TYPE_PIPELINE_STATISTICS`.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateQueryPool.html">vkCreateQueryPool Manual Page</a>
     */
    fun createQueryPool(createInfo: VkQueryPoolCreateInfo.() -> Unit): QueryPool = memScoped {
        val queryPoolCreateInfo = alloc<VkQueryPoolCreateInfo> {
            sType = VK_STRUCTURE_TYPE_QUERY_POOL_CREATE_INFO
            createInfo()
        }
        val queryPoolVar = alloc<VkQueryPoolVar>()
        dispatch.vkCreateQueryPool!!(handle, queryPoolCreateInfo.ptr, null, queryPoolVar.ptr)
            .checkResult("Failed to create query pool")
        val valuesPerQuery = when (queryPoolCreateInfo.queryType) {
            VK_QUERY_TYPE_PIPELINE_STATISTICS -> queryPoolCreateInfo.pipelineStatistics.countOneBits()
            VK_QUERY_TYPE_TRANSFORM_FEEDBACK_STREAM_EXT -> 2
            VK_QUERY_TYPE_RESULT_STATUS_ONLY_KHR -> 0
            else -> 1
        }
        return QueryPool(this@Device, queryPoolVar.value!!, valuesPerQuery)
    }

    /**
     * Create a new sampler.
     *
     * Enabling anisotropy requires the `samplerAnisotropy` feature to be enabled on the device.
     * `VK_SAMPLER_ADDRESS_MODE_MIRROR_CLAMP_TO_EDGE` requires `samplerMirrorClampToEdge`.
     * Minimum or maximum reduction modes in a chained `VkSamplerReductionModeCreateInfo` require `samplerFilterMinmax`.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateSampler.html">vkCreateSampler Manual Page</a>
     */
    fun createSampler(createInfo: VkSamplerCreateInfo.() -> Unit): Sampler = memScoped {
        val samplerCreateInfo = alloc<VkSamplerCreateInfo> {
            sType = VK_STRUCTURE_TYPE_SAMPLER_CREATE_INFO
            createInfo()
        }
        val samplerVar = alloc<VkSamplerVar>()
        dispatch.vkCreateSampler!!(handle, samplerCreateInfo.ptr, null, samplerVar.ptr)
            .checkResult("Failed to create sampler")
        return Sampler(this@Device, samplerVar.value!!)
    }

    /**
     * Create a new semaphore.
     *
     * Requires the `timelineSemaphore` feature to be enabled on the device when [semaphoreType] is
     * `VK_SEMAPHORE_TYPE_TIMELINE`.
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
        dispatch.vkCreateSemaphore!!(handle, semaphoreInfo.ptr, null, semaphore.ptr)
            .checkResult("Failed to create a semaphore")
        return Semaphore(this@Device, semaphore.value!!, semaphoreType)
    }

    /**
     * Create a new shader module from SPIR-V code.
     *
     * [code] must meet the requirements of [shaderModuleCreateInfo] and may start at any address. It is read only
     * during this call and is not retained.
     *
     * A pipeline can also take SPIR-V code without a shader module object: see [shaderModuleCreateInfo].
     *
     * @param code The memory region holding the SPIR-V code.
     * @param flags Shader module creation flags. Vulkan defines none yet, so the default is the only valid value.
     * @param extensions Optional `pNext` chain of extension structures to attach to `VkShaderModuleCreateInfo`,
     * such as `VkShaderModuleValidationCacheCreateInfoEXT`. The chained structures and any memory they reference
     * must remain valid until this call returns.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCreateShaderModule.html">vkCreateShaderModule Manual Page</a>
     */
    fun createShaderModule(code: MemoryRegion, flags: VkShaderModuleCreateFlags = 0u, extensions: COpaquePointer? = null): ShaderModule =
        memScoped {
            val createInfo = shaderModuleCreateInfo(code)
            createInfo.pNext = extensions
            createInfo.flags = flags
            val shaderModule = alloc<VkShaderModuleVar>()
            dispatch.vkCreateShaderModule!!(handle, createInfo.ptr, null, shaderModule.ptr)
                .checkResult("Failed to create shader module")
            return ShaderModule(this@Device, shaderModule.value!!)
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
        dispatch.vkCreateSwapchainKHR!!(handle, swapChainCreateInfo.ptr, null, swapChainVar.ptr)
            .checkResult("Failed to create a swap chain")
        return Swapchain(this@Device, swapChainVar.value!!)
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
        dispatch.vkGetDescriptorSetLayoutSupport!!(handle, createInfo.ptr, layoutSupport.ptr)
        return layoutSupport.supported == VK_TRUE
    }

    /**
     * Determine memory requirements for a buffer without creating one, including dedicated-allocation flags.
     *
     * @param createInfo Configures the buffer creation parameters to query.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetDeviceBufferMemoryRequirements.html">vkGetDeviceBufferMemoryRequirements Manual Page</a>
     */
    fun getDeviceBufferMemoryRequirements(createInfo: VkBufferCreateInfo.() -> Unit): MemoryRequirements = memScoped {
        val bufferCreateInfo = alloc<VkBufferCreateInfo> {
            sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO
            createInfo()
        }
        val requirementsInfo = alloc<VkDeviceBufferMemoryRequirements> {
            sType = VK_STRUCTURE_TYPE_DEVICE_BUFFER_MEMORY_REQUIREMENTS
            pCreateInfo = bufferCreateInfo.ptr
        }
        val dedicatedRequirements = alloc<VkMemoryDedicatedRequirements> {
            sType = VK_STRUCTURE_TYPE_MEMORY_DEDICATED_REQUIREMENTS
        }
        val memoryRequirements = alloc<VkMemoryRequirements2> {
            sType = VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
            pNext = dedicatedRequirements.ptr
        }
        dispatch.vkGetDeviceBufferMemoryRequirements!!(handle, requirementsInfo.ptr, memoryRequirements.ptr)
        return memoryRequirements.memoryRequirements.toMemoryRequirements(dedicatedRequirements)
    }

    /**
     * Determine memory requirements for an image without creating one, including dedicated-allocation flags.
     *
     * When [createInfo] specifies a multi-planar format with `VK_IMAGE_CREATE_DISJOINT_BIT`, [planeAspect] must
     * specify a single valid multi-planar aspect bit (such as `VK_IMAGE_ASPECT_PLANE_0_BIT`). Otherwise, pass
     * `VK_IMAGE_ASPECT_NONE`.
     *
     * @param planeAspect The aspect of the disjoint multi-planar image plane to query, or `VK_IMAGE_ASPECT_NONE`.
     * @param createInfo Configures the image creation parameters to query.
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetDeviceImageMemoryRequirements.html">vkGetDeviceImageMemoryRequirements Manual Page</a>
     */
    fun getDeviceImageMemoryRequirements(
        planeAspect: VkImageAspectFlagBits = VK_IMAGE_ASPECT_NONE,
        createInfo: VkImageCreateInfo.() -> Unit,
    ): MemoryRequirements = memScoped {
        val imageCreateInfo = alloc<VkImageCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
            createInfo()
        }
        assert(imageCreateInfo.flags and VK_IMAGE_CREATE_DISJOINT_BIT == 0u || planeAspect != VK_IMAGE_ASPECT_NONE) {
            "planeAspect must not be VK_IMAGE_ASPECT_NONE when VK_IMAGE_CREATE_DISJOINT_BIT is set"
        }
        val requirementsInfo = alloc<VkDeviceImageMemoryRequirements> {
            sType = VK_STRUCTURE_TYPE_DEVICE_IMAGE_MEMORY_REQUIREMENTS
            pCreateInfo = imageCreateInfo.ptr
            this.planeAspect = planeAspect
        }
        val dedicatedRequirements = alloc<VkMemoryDedicatedRequirements> {
            sType = VK_STRUCTURE_TYPE_MEMORY_DEDICATED_REQUIREMENTS
        }
        val memoryRequirements = alloc<VkMemoryRequirements2> {
            sType = VK_STRUCTURE_TYPE_MEMORY_REQUIREMENTS_2
            pNext = dedicatedRequirements.ptr
        }
        dispatch.vkGetDeviceImageMemoryRequirements!!(handle, requirementsInfo.ptr, memoryRequirements.ptr)
        return memoryRequirements.memoryRequirements.toMemoryRequirements(dedicatedRequirements)
    }

    /**
     * Retrieve layout information for an image subresource described by image creation parameters.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetDeviceImageSubresourceLayout.html">vkGetDeviceImageSubresourceLayout Manual Page</a>
     */
    fun getDeviceImageSubresourceLayout(
        imageCreateInfo: VkImageCreateInfo.() -> Unit,
        aspectMask: VkImageAspectFlags,
        mipLevel: UInt = 0u,
        arrayLayer: UInt = 0u,
    ): ImageSubresourceLayout = memScoped {
        val createInfo = alloc<VkImageCreateInfo> {
            sType = VK_STRUCTURE_TYPE_IMAGE_CREATE_INFO
            imageCreateInfo()
        }
        val subresource = alloc<VkImageSubresource2> {
            sType = VK_STRUCTURE_TYPE_IMAGE_SUBRESOURCE_2
            imageSubresource.aspectMask = aspectMask
            imageSubresource.mipLevel = mipLevel
            imageSubresource.arrayLayer = arrayLayer
        }
        val deviceImageSubresourceInfo = alloc<VkDeviceImageSubresourceInfo> {
            sType = VK_STRUCTURE_TYPE_DEVICE_IMAGE_SUBRESOURCE_INFO
            pCreateInfo = createInfo.ptr
            pSubresource = subresource.ptr
        }
        val hostMemcpySize = alloc<VkSubresourceHostMemcpySize> {
            sType = VK_STRUCTURE_TYPE_SUBRESOURCE_HOST_MEMCPY_SIZE
        }
        val subresourceLayout = alloc<VkSubresourceLayout2> {
            sType = VK_STRUCTURE_TYPE_SUBRESOURCE_LAYOUT_2
            pNext = hostMemcpySize.ptr
        }
        dispatch.vkGetDeviceImageSubresourceLayout!!(handle, deviceImageSubresourceInfo.ptr, subresourceLayout.ptr)
        return subresourceLayout.subresourceLayout.toImageSubresourceLayout(hostMemcpySize.size)
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
        dispatch.vkGetImageSubresourceLayout2!!(handle, image.handle, subresource.ptr, subresourceLayout.ptr)
        return subresourceLayout.subresourceLayout.toImageSubresourceLayout(hostMemcpySize.size)
    }

    /**
     * Get a queue from the device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetDeviceQueue.html">vkGetDeviceQueue Manual Page</a>
     */
    fun getQueue(queueFamilyIndex: UInt, queueIndex: UInt = 0u): Queue = memScoped {
        val queueVar = alloc<VkQueueVar>()
        dispatch.vkGetDeviceQueue!!(handle, queueFamilyIndex, queueIndex, queueVar.ptr)
        return Queue(this@Device, queueVar.value!!, queueFamilyIndex)
    }

    /**
     * Get the optimal render area granularity for a dynamic rendering instance with the supplied attachment formats.
     *
     * This Vulkan 1.4 query describes a non-multiview rendering instance.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkGetRenderingAreaGranularity.html">vkGetRenderingAreaGranularity Manual Page</a>
     */
    fun getRenderingAreaGranularity(
        colorAttachmentFormats: List<VkFormat> = emptyList(),
        depthAttachmentFormat: VkFormat = VK_FORMAT_UNDEFINED,
        stencilAttachmentFormat: VkFormat = VK_FORMAT_UNDEFINED,
    ): Extent2D = memScoped {
        val colorFormats = if (colorAttachmentFormats.isNotEmpty()) {
            allocArray<UIntVar>(colorAttachmentFormats.size) { index ->
                value = colorAttachmentFormats[index]
            }
        } else {
            null
        }
        val renderingAreaInfo = alloc<VkRenderingAreaInfo> {
            sType = VK_STRUCTURE_TYPE_RENDERING_AREA_INFO
            viewMask = 0u
            colorAttachmentCount = colorAttachmentFormats.size.toUInt()
            pColorAttachmentFormats = colorFormats
            this.depthAttachmentFormat = depthAttachmentFormat
            this.stencilAttachmentFormat = stencilAttachmentFormat
        }
        val granularity = alloc<VkExtent2D>()
        dispatch.vkGetRenderingAreaGranularity!!(handle, renderingAreaInfo.ptr, granularity.ptr)
        return granularity.toExtent2D()
    }

    /**
     * Update descriptor sets.
     *
     * The supplied structures and any memory referenced by their pointers must remain valid until this call returns.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkUpdateDescriptorSets.html">vkUpdateDescriptorSets Manual Page</a>
     */
    fun updateDescriptorSets(writes: List<VkWriteDescriptorSet>, copies: List<VkCopyDescriptorSet> = emptyList()): Unit = memScoped {
        val writesArray = if (writes.isNotEmpty()) {
            allocArray<VkWriteDescriptorSet>(writes.size) { index ->
                writes[index].readValue().place(ptr)
            }
        } else {
            null
        }

        val copiesArray = if (copies.isNotEmpty()) {
            allocArray<VkCopyDescriptorSet>(copies.size) { index ->
                copies[index].readValue().place(ptr)
            }
        } else {
            null
        }

        dispatch.vkUpdateDescriptorSets!!(
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
        dispatch.vkDeviceWaitIdle!!(handle).checkResult("Failed to wait for device idle")
    }

    /**
     * Destroy the logical device.
     *
     * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkDestroyDevice.html">vkDestroyDevice Manual Page</a>
     */
    override fun close() {
        dispatch.vkDestroyDevice!!(handle, null)
        dispatchLoader.unload(dispatchTable)
        instance = null
    }

    /**
     * Creates a compute pipeline, chaining [creationFeedbackCreateInfo] to its creation parameters when supplied.
     */
    private fun createComputePipelineImpl(
        layout: PipelineLayout?,
        shaderStage: VkPipelineShaderStageCreateInfo.() -> Unit,
        flags: VkPipelineCreateFlags2,
        basePipeline: Pipeline?,
        cache: PipelineCache?,
        creationFeedbackCreateInfo: VkPipelineCreationFeedbackCreateInfo?,
    ): Pipeline? = memScoped {
        val flagsCreateInfo = alloc<VkPipelineCreateFlags2CreateInfo> {
            sType = VK_STRUCTURE_TYPE_PIPELINE_CREATE_FLAGS_2_CREATE_INFO
            this.flags = flags
        }
        creationFeedbackCreateInfo?.pNext = flagsCreateInfo.ptr
        val computePipelineCreateInfo = alloc<VkComputePipelineCreateInfo> {
            sType = VK_STRUCTURE_TYPE_COMPUTE_PIPELINE_CREATE_INFO
            pNext = creationFeedbackCreateInfo?.ptr ?: flagsCreateInfo.ptr
            this.layout = layout?.handle
            basePipelineHandle = basePipeline?.handle
            basePipelineIndex = -1
            stage.apply {
                sType = VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO
                shaderStage()
            }
        }
        val pipelineVar = alloc<VkPipelineVar>()
        val result = dispatch.vkCreateComputePipelines!!(
            handle,
            cache?.handle,
            1u,
            computePipelineCreateInfo.ptr,
            null,
            pipelineVar.ptr,
        )
        return result.toPipelineOrNull(this@Device, pipelineVar.value, "Failed to create compute pipeline")
    }

    /**
     * Creates a graphics pipeline, chaining [creationFeedbackCreateInfo] to its creation parameters when supplied.
     */
    private fun createGraphicsPipelineImpl(
        layout: PipelineLayout?,
        stageCount: UInt,
        stages: VkPipelineShaderStageCreateInfo.(UInt) -> Unit,
        vertexInputState: VkPipelineVertexInputStateCreateInfo.() -> Unit,
        inputAssemblyState: VkPipelineInputAssemblyStateCreateInfo.() -> Unit,
        tessellationState: VkPipelineTessellationStateCreateInfo.() -> Unit,
        viewportState: VkPipelineViewportStateCreateInfo.() -> Unit,
        rasterizationState: VkPipelineRasterizationStateCreateInfo.() -> Unit,
        multisampleState: VkPipelineMultisampleStateCreateInfo.() -> Unit,
        depthStencilState: VkPipelineDepthStencilStateCreateInfo.() -> Unit,
        colorBlendState: VkPipelineColorBlendStateCreateInfo.() -> Unit,
        dynamicState: VkPipelineDynamicStateCreateInfo.() -> Unit,
        renderingCreateInfo: VkPipelineRenderingCreateInfo.() -> Unit,
        flags: VkPipelineCreateFlags2,
        basePipeline: Pipeline?,
        cache: PipelineCache?,
        creationFeedbackCreateInfo: VkPipelineCreationFeedbackCreateInfo?,
    ): Pipeline? = memScoped {
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
        if (creationFeedbackCreateInfo != null) {
            assert(!renderingCreateInfo.pNext.containsStructure(VK_STRUCTURE_TYPE_PIPELINE_CREATION_FEEDBACK_CREATE_INFO)) {
                "renderingCreateInfo must not chain VkPipelineCreationFeedbackCreateInfo when creation feedback is requested"
            }
            // Prepending keeps the structures the caller chained through renderingCreateInfo reachable.
            creationFeedbackCreateInfo.pNext = flagsCreateInfo.ptr
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
            pNext = creationFeedbackCreateInfo?.ptr ?: flagsCreateInfo.ptr
            basePipelineHandle = basePipeline?.handle
            basePipelineIndex = -1
        }
        val pipelineVar = alloc<VkPipelineVar>()
        val result = dispatch.vkCreateGraphicsPipelines!!(
            handle,
            cache?.handle,
            1u,
            graphicsPipelineCreateInfo.ptr,
            null,
            pipelineVar.ptr,
        )
        return result.toPipelineOrNull(this@Device, pipelineVar.value, "Failed to create graphics pipeline")
    }
}
