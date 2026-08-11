import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest
import org.jetbrains.kotlin.konan.target.HostManager

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
    plugins {
        val conventionPluginsVersion = "v52"
        id("io.technoirlab.conventions.kotlin-multiplatform-application") version conventionPluginsVersion
        id("io.technoirlab.conventions.kotlin-multiplatform-library") version conventionPluginsVersion
        id("io.technoirlab.conventions.root") version conventionPluginsVersion
        id("io.technoirlab.conventions.settings") version conventionPluginsVersion
        id("io.technoirlab.vfs-overlay") version "1.0.1"
    }
}

plugins {
    id("io.technoirlab.conventions.kotlin-multiplatform-application") apply false
    id("io.technoirlab.conventions.kotlin-multiplatform-library") apply false
    id("io.technoirlab.conventions.root") apply false
    id("io.technoirlab.conventions.settings")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

globalSettings {
    projectId = "vulkan-kotlin"

    metadata {
        description = "Kotlin Multiplatform bindings for Vulkan API."
        developer(name = "technoir", email = "technoir.dev@gmail.com")
        license(name = "The Apache Software License, Version 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.txt")
    }
}

gradle.lifecycle.afterProject {
    pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
        val vulkanSdkDir = providers.environmentVariable("VULKAN_SDK")
        tasks.withType<KotlinNativeTest>().configureEach {
            if (HostManager.hostIsMac) {
                // macOS purges dyld environment variables when launching protected processes,
                // so we have to define DYLD_LIBRARY_PATH ourselves
                vulkanSdkDir.orNull?.let { environment("DYLD_LIBRARY_PATH", "$it/lib") }
            }
        }
    }
}

include(":sample")
include(":volk-kotlin")
include(":vulkan-kotlin")
