pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
    plugins {
        val conventionPluginsVersion = "v38"
        id("io.technoirlab.conventions.kotlin-multiplatform-application") version conventionPluginsVersion
        id("io.technoirlab.conventions.kotlin-multiplatform-library") version conventionPluginsVersion
        id("io.technoirlab.conventions.root") version conventionPluginsVersion
        id("io.technoirlab.conventions.settings") version conventionPluginsVersion
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
        license(name = "The Apache Software License, Version 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.txt")
    }
}

include(":sample")
include(":volk-kotlin")
include(":vulkan-kotlin")
