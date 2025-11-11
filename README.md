Vulkan Kotlin
=============

[![Build](https://github.com/technoir-lab/vulkan-kotlin/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/technoir-lab/vulkan-kotlin/actions/workflows/build.yaml)
[![Maven Central Version](https://img.shields.io/maven-central/v/io.technoirlab.vulkan/vulkan-kotlin)](https://central.sonatype.com/artifact/io.technoirlab.vulkan/vulkan-kotlin)

Kotlin Multiplatform bindings for Vulkan API, which add features like type safety, RAII,
exceptions and integration with the Kotlin ecosystem.

## Getting Started

Add entries to the version catalog:

```toml
[versions]
vulkan-kotlin = "1.4.328-3"

[libraries]
vulkan-kotlin = { module = "io.technoirlab.vulkan:vulkan-kotlin", version.ref = "vulkan-kotlin" }
```

Add dependency to a Kotlin Multiplatform project:

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

For more information, please see the [API reference](https://technoir-lab.github.io/vulkan-kotlin/) and the [sample](sample) project.

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
