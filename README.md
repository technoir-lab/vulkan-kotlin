Vulkan Kotlin
=============

[![Build](https://github.com/technoir-lab/vulkan-kotlin/actions/workflows/ci.yaml/badge.svg?branch=main)](https://github.com/technoir-lab/vulkan-kotlin/actions/workflows/ci.yaml)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.technoirlab.vulkan/vulkan-kotlin)](https://central.sonatype.com/artifact/io.technoirlab.vulkan/vulkan-kotlin)

Kotlin Multiplatform bindings for Vulkan API, which add features like type safety, RAII,
exceptions and integration with the Kotlin ecosystem.

## Requirements

* Kotlin 2.4.0 or later
* Vulkan 1.4 or later

## Getting Started

Add entries to the version catalog:

```toml
[versions]
vulkan-kotlin = "1.4.350-1"

[libraries]
vulkan-kotlin = { module = "io.technoirlab.vulkan:vulkan-kotlin", version.ref = "vulkan-kotlin" }
```

Add vulkan-kotlin dependency to a Kotlin Multiplatform project:

```kotlin
kotlin {
   sourceSets.commonMain {
       dependencies {
           implementation(libs.vulkan.kotlin)
       }
   }
}
```

Load Vulkan API and create an instance:

```kotlin
val vulkan = Vulkan()
val instance = vulkan.createInstance()
```

On Apple platforms, `Vulkan()` appends bundled SwiftPM driver manifests to `VK_ADD_DRIVER_FILES`
and validation layer directories to `VK_ADD_LAYER_PATH` before initializing the loader.
Existing entries are preserved, and bundled and system-installed ICDs are discovered together.
The loader's [environment variable rules](https://github.com/KhronosGroup/Vulkan-Loader/blob/main/docs/LoaderDriverInterface.md#driver-discovery)
still apply: `VK_DRIVER_FILES` takes precedence over the deprecated `VK_ICD_FILENAMES`, and
either overrides additive and default driver discovery. `VK_LAYER_PATH` overrides additive
explicit-layer discovery. Driver selection and disable filters are left unchanged. Applications select
physical devices according to their requirements and must still package the runtime libraries
and resource bundles with valid manifest paths.

For more information, please see the [API reference](https://technoir-lab.github.io/vulkan-kotlin/) and the [sample](sample) project.

On Android, add the following as a direct child of the `<manifest>` element in `AndroidManifest.xml`:

```xml
<uses-feature
    android:name="android.hardware.vulkan.version"
    android:version="0x00404000"
    android:required="true" />
```

### Debug assertions

Kotlin wrappers use assertions to check for invalid API usage. By default, assertions are disabled and get stripped by the Kotlin compiler.
To enable assertions for debug binaries, add the following to the application module:

```kotlin
kotlin {
    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.configureEach {
            if (buildType == NativeBuildType.DEBUG) {
                freeCompilerArgs += "-ea"
            }
        }
    }
}
```

## Supported targets

* androidNativeArm64
* iosArm64
* iosSimulatorArm64
* linuxArm64
* linuxX64
* macosArm64
* mingwX64

## Unsupported functionality

* Render passes and framebuffers (de-facto deprecated)
* Custom memory allocators (will be added in the future)
* Raytracing (will be added in the future)
* Multiview
