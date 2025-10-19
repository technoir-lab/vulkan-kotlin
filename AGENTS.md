Vulkan Kotlin
=============

This library is a thin object-oriented Kotlin Multiplatform wrapper for Vulkan API 1.4.
Each Vulkan's handle type has a corresponding RAII wrapper, e.g. `VkDevice` is wrapped by `Device`, `VkInstance` - `Instance`.

The project leverages exceptions for error handling, and context parameters for scoped memory allocations.
The goal of the wrapper is to provide full coverage of Vulkan API and extensions, except for
any deprecated or intentionally unsupported functionality.

Intentionally unsupported functionality:
* Render passes and framebuffers (de-facto deprecated in Vulkan API 1.4)
* Custom memory allocators (will be added in the future)
* Compute (will be added in the future)
* Raytracing (will be added in the future)
* Multiview

## Supported platforms

* Android
* Linux
* macOS
* iOS
* Windows

## Dependencies

* [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime) - multiplatform date and time library.
* [kotlinx-io](https://github.com/Kotlin/kotlinx-io) - multiplatform I/O library.
* [volk](https://github.com/zeux/volk) - meta loader for Vulkan API.

## Project structure

* `volk-kotlin` - Kotlin/Native C-interop wrapper for Vulkan API and Volk.
* `vulkan-kotlin` - Kotlin Multiplatform wrapper for Vulkan API.
* `sample` - sample application.

## Hardware requirements

The wrapper targets the modern desktop and mobile GPUs.
Vulkan 1.3 is the minimum required driver version.

Required extensions:
* `VK_KHR_dynamic_rendering`
* `VK_KHR_dynamic_rendering_local_read`
* `VK_EXT_extended_dynamic_state2`
* `VK_KHR_synchronization2`

## Coding conventions

* The functions inside each class should be sorted by visibility (public then private), then lexicographically.
* `Device` class acts as the factory for most of the other classes that need `VkDevice` for their creation.
* When native memory allocation is required inside a function, `NativePlacement` should be passed as a context parameter.
  The caller is responsible for handling the allocation and freeing the memory.
* When the same functionality is available both in core and as an extension, the core functionality must be used.
* Every `public` class, function and property must have a KDoc.

## Building and testing

* Run tests and static analysis: `./gradlew check`
