package io.technoirlab.volk

import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalForeignApi::class)
class VolkTest {
    @Test
    fun initialization() {
        val result = volkInitialize()
        assertEquals(VK_SUCCESS, result)

        val version = volkGetInstanceVersion()
        assertNotEquals(0u, version)
    }
}
