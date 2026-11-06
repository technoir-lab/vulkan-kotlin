plugins {
    id("io.technoirlab.conventions.kotlin-multiplatform-application")
}

kotlin {
    androidNativeArm64()
    iosArm64()
    iosSimulatorArm64()
    linuxArm64()
    linuxX64()
    macosArm64 {
        binaries.framework {
            baseName = project.name
            isStatic = true
        }
    }
    mingwX64()

    compilerOptions {
        optIn.add("kotlinx.cinterop.ExperimentalForeignApi")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":vulkan-kotlin"))
            }
        }
    }
}
