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
}

vfsOverlay {
    mapping(
        source = kotlinNativeDependenciesDir.map {
            File(it, "target-toolchain-2-${HostManager.hostOs()}-android_ndk/sysroot/usr/include/vulkan")
        },
        target = providers.environmentVariable("VULKAN_SDK").map {
            File(it, "${if (HostManager.hostIsMingw) "Include" else "include"}/vulkan")
        }
    )
}
