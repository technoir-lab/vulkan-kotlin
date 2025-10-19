plugins {
    id("io.technoirlab.conventions.kotlin-multiplatform-library")
    id("vfs-overlay")
}

kotlinMultiplatformLibrary {
    packageName = "io.technoirlab.volk"

    buildFeatures {
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
