Vulkan Kotlin
=============

This library provides thin object-oriented Kotlin Multiplatform bindings for Vulkan API 1.4.
Wrapped Vulkan handle types use `AutoCloseable` RAII objects; for example, `VkDevice` is wrapped by `Device` and
`VkInstance` by `Instance`.

The project leverages exceptions for error handling, and context parameters for scoped memory allocations.
The goal of the wrapper is to provide full coverage of Vulkan API and extensions, except for
any deprecated or intentionally unsupported functionality.

Intentionally unsupported functionality:
* Render passes and framebuffers (de-facto deprecated in Vulkan API 1.4)
* Custom memory allocators (will be added in the future)
* Raytracing (will be added in the future)
* Multiview

## Supported targets

* androidNativeArm64
* iosArm64
* iosSimulatorArm64
* linuxArm64
* linuxX64
* macosArm64
* mingwX64

## Dependencies

* [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime) - multiplatform date and time library.
* [kotlinx-io](https://github.com/Kotlin/kotlinx-io) - multiplatform I/O library.
* [volk](https://github.com/zeux/volk) - meta loader for Vulkan API.

## Project structure

* `volk-kotlin` - Kotlin/Native C-interop wrapper for Vulkan API and Volk.
* `vulkan-kotlin` - Kotlin Multiplatform bindings. Core objects remain in `io.technoirlab.vulkan`; other
  object families live in the `command`, `debug`, `descriptor`, `pipeline`, `presentation`, `query`, `resource`,
  and `sync` subpackages.
* `sample` - Kotlin Multiplatform sample application.

## Hardware requirements

The wrapper targets the modern desktop and mobile GPUs.
Vulkan 1.4 is the minimum required driver version.

Required core features (enabled by default when creating a device):
* `dynamicRendering`
* `synchronization2`
* `dynamicRenderingLocalRead`

The Vulkan 1.3 core subset of extended dynamic state 2 is required. No extension or feature enablement is needed for
`vkCmdSetDepthBiasEnable`, `vkCmdSetPrimitiveRestartEnable`, or `vkCmdSetRasterizerDiscardEnable`.

## Coding conventions

* The functions inside each class should be sorted by visibility (public then private), then lexicographically.
* `Device` class acts as the factory for most of the other classes that need `VkDevice` for their creation.
* When native memory allocation is required inside a function, `NativePlacement` should be passed as a context parameter.
  The caller is responsible for handling the allocation and freeing the memory.
* When the same functionality is available both in core and as an extension, the core functionality must be used.
* Use only `kotlin.assert` for input and constraint validation; never use `require`, `requireNotNull`, `check`, or `checkNotNull`.
* Every `public` class, function, and property must have a KDoc.

## Building and testing

* A Vulkan SDK providing the headers and loader library is required. CI currently builds against Vulkan SDK
  1.4.357.0; keep `.github/workflows/ci.yaml` and `.github/workflows/release.yaml` in sync when changing it.
* Run build, tests, ABI validation, and static analysis: `./gradlew check`.
* Run `./gradlew` commands outside the filesystem sandbox so Gradle can access its cache.
* CI builds on macOS 26 and Ubuntu 24.04. macOS Vulkan loader tests use `VULKAN_SDK` to set `DYLD_LIBRARY_PATH`.
