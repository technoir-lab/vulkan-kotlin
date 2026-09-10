import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    id("io.technoirlab.conventions.kotlin-multiplatform-library")
    id("io.technoirlab.vfs-overlay")
}

kotlinMultiplatformLibrary {
    packageName = "io.technoirlab.volk"

    buildFeatures {
        abiValidation = true
        cinterop = true
    }
}

kotlin {
    androidNativeArm64()
    iosArm64()
    iosSimulatorArm64()
    linuxArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

kotlin {
    swiftPMDependencies {
        macosMinimumDeploymentTarget.set("26.0")
        iosMinimumDeploymentTarget.set("26.0")
        discoverClangModulesImplicitly.set(false)

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        swiftPackage(
            packageName = "vulkan-swift",
            products = listOf(product("VulkanDriver"), product("VulkanValidation")),
            url = url("https://github.com/technoir-lab/vulkan-swift"),
            version = exact("1.0.2"),
        )
    }
}

vfsOverlay {
    mapping(
        source = kotlinNativeDependenciesDir.map {
            File(it, "target-toolchain-2-${HostManager.hostOs()}-android_ndk/sysroot/usr/include/vulkan")
        },
        target = providers.environmentVariable("VULKAN_SDK").map {
            File(it, "${if (HostManager.hostIsMingw) "Include" else "include"}/vulkan")
        },
    )
}
