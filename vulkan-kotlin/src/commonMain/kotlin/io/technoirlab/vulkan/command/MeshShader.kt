package io.technoirlab.vulkan.command

import io.technoirlab.volk.vkCmdDrawMeshTasksEXT
import io.technoirlab.volk.vkCmdDrawMeshTasksIndirectCountEXT
import io.technoirlab.volk.vkCmdDrawMeshTasksIndirectEXT
import io.technoirlab.vulkan.resource.Buffer
import kotlinx.cinterop.invoke
import kotlin.assert

private const val DRAW_COUNT_SIZE = 4uL
private const val DRAW_MESH_TASKS_INDIRECT_COMMAND_SIZE = 12u

/**
 * Draw mesh task work items.
 * Requires the `VK_EXT_mesh_shader` extension and the `meshShader` feature.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawMeshTasksEXT.html">vkCmdDrawMeshTasksEXT Manual Page</a>
 */
fun CommandBuffer.drawMeshTasks(groupCountX: UInt, groupCountY: UInt = 1u, groupCountZ: UInt = 1u) {
    vkCmdDrawMeshTasksEXT!!(handle, groupCountX, groupCountY, groupCountZ)
}

/**
 * Draw mesh task work items with parameters read from a buffer of `VkDrawMeshTasksIndirectCommandEXT` structures.
 * Requires the `VK_EXT_mesh_shader` extension and the `meshShader` feature.
 * The `multiDrawIndirect` feature must be enabled when [drawCount] is greater than one.
 * The stride is ignored when [drawCount] is zero or one.
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawMeshTasksIndirectEXT.html">vkCmdDrawMeshTasksIndirectEXT Manual Page</a>
 */
fun CommandBuffer.drawMeshTasksIndirect(
    buffer: Buffer,
    offset: ULong = 0uL,
    drawCount: UInt = 1u,
    stride: UInt = DRAW_MESH_TASKS_INDIRECT_COMMAND_SIZE,
) {
    assert(offset % 4uL == 0uL) { "offset must be a multiple of 4" }
    assert(drawCount <= 1u || stride % 4u == 0u && stride >= DRAW_MESH_TASKS_INDIRECT_COMMAND_SIZE) {
        "stride must be a multiple of 4 and must be greater than or equal to sizeof(VkDrawMeshTasksIndirectCommandEXT)"
    }
    vkCmdDrawMeshTasksIndirectEXT!!(handle, buffer.handle, offset, drawCount, stride)
}

/**
 * Draw mesh task work items with parameters and the draw count read from buffers.
 * Requires the `VK_EXT_mesh_shader` extension and the `meshShader` and `drawIndirectCount` features.
 * The draw count is the minimum of the value in [countBuffer] and [maxDrawCount].
 *
 * @see <a href="https://registry.khronos.org/vulkan/specs/latest/man/html/vkCmdDrawMeshTasksIndirectCountEXT.html">vkCmdDrawMeshTasksIndirectCountEXT Manual Page</a>
 */
fun CommandBuffer.drawMeshTasksIndirectCount(
    buffer: Buffer,
    offset: ULong = 0uL,
    countBuffer: Buffer,
    countBufferOffset: ULong = 0uL,
    maxDrawCount: UInt,
    stride: UInt = DRAW_MESH_TASKS_INDIRECT_COMMAND_SIZE,
) {
    assert(offset % 4uL == 0uL) { "offset must be a multiple of 4" }
    assert(countBufferOffset % 4uL == 0uL) { "countBufferOffset must be a multiple of 4" }
    assert(stride % 4u == 0u && stride >= DRAW_MESH_TASKS_INDIRECT_COMMAND_SIZE) {
        "stride must be a multiple of 4 and must be greater than or equal to sizeof(VkDrawMeshTasksIndirectCommandEXT)"
    }
    assert(countBuffer.size >= DRAW_COUNT_SIZE && countBufferOffset <= countBuffer.size - DRAW_COUNT_SIZE) {
        "countBufferOffset + sizeof(uint32_t) must be less than or equal to ${countBuffer.size}"
    }
    vkCmdDrawMeshTasksIndirectCountEXT!!(
        handle,
        buffer.handle,
        offset,
        countBuffer.handle,
        countBufferOffset,
        maxDrawCount,
        stride,
    )
}
