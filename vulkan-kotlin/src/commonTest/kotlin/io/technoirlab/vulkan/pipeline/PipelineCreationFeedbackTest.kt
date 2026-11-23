package io.technoirlab.vulkan.pipeline

import io.technoirlab.volk.VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT
import io.technoirlab.volk.VK_PIPELINE_CREATION_FEEDBACK_BASE_PIPELINE_ACCELERATION_BIT
import io.technoirlab.volk.VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT
import io.technoirlab.volk.VkPipelineCreationFeedback
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PipelineCreationFeedbackTest {
    @Test
    fun `feedback without the valid bit is absent`(): Unit = memScoped {
        val native = alloc<VkPipelineCreationFeedback> { duration = 42uL }

        val feedback = native.toPipelineCreationFeedback()

        assertNull(feedback)
    }

    @Test
    fun `flags set without the valid bit are not reported`(): Unit = memScoped {
        val native = alloc<VkPipelineCreationFeedback> {
            flags = VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT or
                VK_PIPELINE_CREATION_FEEDBACK_BASE_PIPELINE_ACCELERATION_BIT
            duration = 42uL
        }

        val feedback = native.toPipelineCreationFeedback()

        assertNull(feedback)
    }

    @Test
    fun `valid feedback with zero duration and no cache hit is present`(): Unit = memScoped {
        val native = alloc<VkPipelineCreationFeedback> { flags = VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT }

        val feedback = native.toPipelineCreationFeedback()

        assertNotNull(feedback)
        assertEquals(0uL, feedback.duration)
        assertFalse(feedback.applicationPipelineCacheHit)
        assertFalse(feedback.basePipelineAcceleration)
    }

    @Test
    fun `duration beyond the signed range is preserved`(): Unit = memScoped {
        val native = alloc<VkPipelineCreationFeedback> {
            flags = VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT
            duration = ULong.MAX_VALUE
        }

        val feedback = native.toPipelineCreationFeedback()

        assertEquals(ULong.MAX_VALUE, feedback?.duration)
    }

    @Test
    fun `application pipeline cache hit is decoded independently of base pipeline acceleration`(): Unit = memScoped {
        val native = alloc<VkPipelineCreationFeedback> {
            flags = VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT or VK_PIPELINE_CREATION_FEEDBACK_APPLICATION_PIPELINE_CACHE_HIT_BIT
        }

        val feedback = native.toPipelineCreationFeedback()

        assertNotNull(feedback)
        assertTrue(feedback.applicationPipelineCacheHit)
        assertFalse(feedback.basePipelineAcceleration)
    }

    @Test
    fun `base pipeline acceleration is decoded independently of application pipeline cache hit`(): Unit = memScoped {
        val native = alloc<VkPipelineCreationFeedback> {
            flags = VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT or VK_PIPELINE_CREATION_FEEDBACK_BASE_PIPELINE_ACCELERATION_BIT
        }

        val feedback = native.toPipelineCreationFeedback()

        assertNotNull(feedback)
        assertFalse(feedback.applicationPipelineCacheHit)
        assertTrue(feedback.basePipelineAcceleration)
    }

    @Test
    fun `flags are preserved raw including bits the wrapper does not decode`(): Unit = memScoped {
        val rawFlags = VK_PIPELINE_CREATION_FEEDBACK_VALID_BIT or UNKNOWN_FLAG
        val native = alloc<VkPipelineCreationFeedback> { flags = rawFlags }

        val feedback = native.toPipelineCreationFeedback()

        assertEquals(rawFlags, feedback?.flags)
    }

    private companion object {
        private const val UNKNOWN_FLAG = 0x8000_0000u
    }
}
