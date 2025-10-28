Vulkan Kotlin
=============

[![Build](https://github.com/technoir-lab/vulkan-kotlin/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/technoir-lab/vulkan-kotlin/actions/workflows/build.yaml) ![Maven Central Version](https://img.shields.io/maven-central/v/io.technoirlab.vulkan/vulkan-kotlin)

Kotlin Multiplatform bindings for Vulkan API, which add features like type safety, RAII,
exceptions and integration with the Kotlin ecosystem.

## Getting Started

Add vulkan-kotlin dependency to your KMP project:

```kotlin
kotlin {
   sourceSets.commonMain {
       dependencies {
           implementation("io.technoirlab.vulkan:vulkan-kotlin:<latest version>")
       }
   }
}
```

Load Vulkan API and create instance:

```kotlin
val vulkan = Vulkan()
val instance = vulkan.createInstance()
...
```

For more information, please see the [API reference](https://technoir-lab.github.io/vulkan-kotlin/) and a [sample](sample) project.

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
