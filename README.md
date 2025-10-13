Vulkan Kotlin
=============

[![License](https://img.shields.io/github/license/technoir-lab/vulkan-kotlin)](LICENSE) [![Build](https://github.com/technoir-lab/vulkan-kotlin/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/technoir-lab/vulkan-kotlin/actions/workflows/build.yaml) ![Maven Central Version](https://img.shields.io/maven-central/v/io.technoirlab.vulkan/vulkan-kotlin)

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
* Compute (will be added in the future)
* Raytracing (will be added in the future)
* Multiview

## License

```
   Copyright 2025 Sergei Chelombitko

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
