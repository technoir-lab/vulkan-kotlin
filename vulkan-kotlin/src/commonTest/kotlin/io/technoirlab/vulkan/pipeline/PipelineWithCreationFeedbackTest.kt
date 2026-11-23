package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT
import io.technoirlab.volk.VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT
import io.technoirlab.volk.VK_STRUCTURE_TYPE_PIPELINE_CREATION_FEEDBACK_CREATE_INFO
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.NativePlacement
import kotlinx.cinterop.alloc
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame

class PipelineWithCreationFeedbackTest {
    @Test
    fun `feedback request has zero-filled storage for the pipeline and every stage`(): Unit = memScoped {
        val createInfo = allocPipelineCreationFeedbackCreateInfo(stageCount = 3u)

        assertEquals(VK_STRUCTURE_TYPE_PIPELINE_CREATION_FEEDBACK_CREATE_INFO, createInfo.sType)
        assertNull(createInfo.pNext)
        assertEquals(0u, createInfo.pPipelineCreationFeedback?.pointed?.flags)
        assertEquals(3u, createInfo.pipelineStageCreationFeedbackCount)
        val stageFeedbacks = assertNotNull(createInfo.pPipelineStageCreationFeedbacks)
        assertEquals(listOf(0u, 0u, 0u), List(3) { stageFeedbacks[it].flags })
    }

    @Test
    fun `feedback request for a pipeline without stages has no stage storage`(): Unit = memScoped {
        val createInfo = allocPipelineCreationFeedbackCreateInfo(stageCount = 0u)

        assertNotNull(createInfo.pPipelineCreationFeedback)
        assertEquals(0u, createInfo.pipelineStageCreationFeedbackCount)
        assertNull(createInfo.pPipelineStageCreationFeedbacks)
    }

    @Test
    fun `feedback the implementation never wrote is absent`(): Unit = memScoped {
        val createInfo = allocPipelineCreationFeedbackCreateInfo(stageCount = 2u)

        val result = createInfo.toPipelineWithCreationFeedback(fakePipeline())

        assertNull(result.pipelineCreationFeedback)
        assertEquals(listOf(null, null), result.pipelineStageCreationFeedbacks)
    }

    @Test
    fun `stage feedback keeps stage order and independent validity`(): Unit = memScoped {
        val pipeline = fakePipeline()
        val createInfo = allocPipelineCreationFeedbackCreateInfo(stageCount = 3u)
        val stageFeedbacks = assertNotNull(createInfo.pPipelineStageCreationFeedbacks)
        stageFeedbacks[0].apply {
            flags = VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT
            duration = 1uL
        }
        stageFeedbacks[1].apply {
            flags = VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT
            duration = 2uL
        }
        stageFeedbacks[2].apply {
            flags = VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT or VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT
            duration = 3uL
        }

        val result = createInfo.toPipelineWithCreationFeedback(pipeline)

        assertSame(pipeline, result.pipeline)
        assertNull(result.pipelineCreationFeedback)
        assertEquals(
            listOf(
                PipelineCreationFeedback(VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT, 1uL),
                null,
                PipelineCreationFeedback(
                    VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT or VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT,
                    3uL,
                ),
            ),
            result.pipelineStageCreationFeedbacks,
        )
    }

    @Test
    fun `pipeline feedback is reported when no stage feedback is`(): Unit = memScoped {
        val createInfo = allocPipelineCreationFeedbackCreateInfo(stageCount = 2u)
        createInfo.pPipelineCreationFeedback?.pointed?.apply {
            flags = VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT or VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT
            duration = 7uL
        }

        val result = createInfo.toPipelineWithCreationFeedback(fakePipeline())

        assertEquals(
            PipelineCreationFeedback(
                VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT or VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT,
                7uL,
            ),
            result.pipelineCreationFeedback,
        )
        assertEquals(listOf(null, null), result.pipelineStageCreationFeedbacks)
    }

    @Test
    fun `pipeline without stages has no stage feedback`(): Unit = memScoped {
        val createInfo = allocPipelineCreationFeedbackCreateInfo(stageCount = 0u)

        val result = createInfo.toPipelineWithCreationFeedback(fakePipeline())

        assertEquals(emptyList(), result.pipelineStageCreationFeedbacks)
    }

    /**
     * A pipeline wrapper over placeholder handles. It reaches no Vulkan implementation and must not be closed.
     */
    private fun NativePlacement.fakePipeline(): Pipeline {
        val storage = alloc<ByteVar>().ptr
        return Pipeline(storage.reinterpret(), storage.reinterpret())
    }
}
