package io.technoirlab.vulkan

import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer

typealias VulkanHandle = CPointer<out CPointed>
