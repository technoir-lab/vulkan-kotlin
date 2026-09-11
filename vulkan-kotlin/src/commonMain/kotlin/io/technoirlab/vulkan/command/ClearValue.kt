package io.technoirlab.vulkan.command

import io.technoirlab.volk.VkClearColorValue
import io.technoirlab.volk.VkClearDepthStencilValue
import io.technoirlab.volk.VkClearValue
import kotlinx.cinterop.set

/**
 * A color or depth/stencil value used to clear an attachment.
 */
sealed interface ClearValue {
    /**
     * A color clear value whose numeric type must match the image or attachment format.
     */
    sealed interface Color : ClearValue {
        /**
         * A floating-point color clear value.
         *
         * @property red The red component.
         * @property green The green component.
         * @property blue The blue component.
         * @property alpha The alpha component.
         */
        data class Float32(
            val red: Float,
            val green: Float,
            val blue: Float,
            val alpha: Float,
        ) : Color

        /**
         * A signed integer color clear value.
         *
         * @property red The red component.
         * @property green The green component.
         * @property blue The blue component.
         * @property alpha The alpha component.
         */
        data class Int32(
            val red: Int,
            val green: Int,
            val blue: Int,
            val alpha: Int,
        ) : Color

        /**
         * An unsigned integer color clear value.
         *
         * @property red The red component.
         * @property green The green component.
         * @property blue The blue component.
         * @property alpha The alpha component.
         */
        data class UInt32(
            val red: UInt,
            val green: UInt,
            val blue: UInt,
            val alpha: UInt,
        ) : Color
    }

    /**
     * A depth/stencil clear value.
     *
     * @property depth The depth clear value.
     * @property stencil The stencil clear value.
     */
    data class DepthStencil(
        val depth: Float,
        val stencil: UInt,
    ) : ClearValue
}

internal fun VkClearColorValue.from(value: ClearValue.Color) {
    when (value) {
        is ClearValue.Color.Float32 -> {
            float32[0] = value.red
            float32[1] = value.green
            float32[2] = value.blue
            float32[3] = value.alpha
        }
        is ClearValue.Color.Int32 -> {
            int32[0] = value.red
            int32[1] = value.green
            int32[2] = value.blue
            int32[3] = value.alpha
        }
        is ClearValue.Color.UInt32 -> {
            uint32[0] = value.red
            uint32[1] = value.green
            uint32[2] = value.blue
            uint32[3] = value.alpha
        }
    }
}

internal inline fun VkClearDepthStencilValue.from(value: ClearValue.DepthStencil) {
    depth = value.depth
    stencil = value.stencil
}

internal inline fun VkClearValue.from(value: ClearValue) {
    when (value) {
        is ClearValue.Color -> color.from(value)
        is ClearValue.DepthStencil -> depthStencil.from(value)
    }
}
