import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest

plugins {
    id("io.technoirlab.conventions.kotlin-multiplatform-library")
}

kotlinMultiplatformLibrary {
    packageName = "io.technoirlab.vulkan"

    buildFeatures {
        abiValidation = true
    }
}

kotlin {
    androidNativeArm64()
    iosArm64()
    iosSimulatorArm64 {
        binaries.configureEach {
            logger.lifecycle("Configuring binary $name in $target")
            linkerOpts("-F$iosVulkanSdkPath/lib/MoltenVK.xcframework/ios-arm64_x86_64-simulator", "-framework", "MoltenVK")
        }
    }
    linuxArm64()
    linuxX64()
    macosArm64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            api(project(":volk-kotlin"))
            api(libs.kotlinx.datetime)
            api(libs.kotlinx.io.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

private val vulkanSdk = providers.environmentVariable("VULKAN_SDK").map { File(it) }
private val iosVulkanSdk = vulkanSdk.map { it.parentFile.resolve("iOS") }
private val iosVulkanSdkPath = iosVulkanSdk.get().absolutePath

val copyMoltenVkForTests = tasks.register<Copy>("copyMoltenVKForIosSimulatorTests") {
    val frameworksOut = layout.buildDirectory.dir("bin/iosSimulatorArm64/debugTest/Frameworks/MoltenVK.framework")
    from("$iosVulkanSdkPath/lib/MoltenVK.xcframework/ios-arm64_x86_64-simulator/MoltenVK.framework")
    into(frameworksOut)

    doLast {
        val destFramework = frameworksOut.get().asFile
        if (!destFramework.exists()) {
            throw GradleException("Expected copied framework at ${destFramework.absolutePath}, but it's missing")
        }
        // ad-hoc sign the framework (sign name "-" means ad-hoc). --deep signs nested contents.
        serviceOf<ExecOperations>().exec {
            commandLine(
                "codesign",
                "--force",
                "--deep",
                "--sign", "-",
                "--timestamp=none",
                destFramework.absolutePath
            )
        }
        logger.lifecycle("Ad-hoc signed: ${destFramework.absolutePath}")
    }
}

tasks.matching { it.name == "iosSimulatorArm64Test" }.configureEach {
    dependsOn(copyMoltenVkForTests)
}

tasks.withType<KotlinNativeTest>().configureEach {
    if ("ios" in name) {
        logger.lifecycle("Configuring test task $name")
        environment("LD_LIBRARY_PATH", "$iosVulkanSdkPath/lib/MoltenVK.xcframework/ios-arm64_x86_64-simulator")
        //environment("DYLD_FRAMEWORK_PATH", "$iosVulkanSdkPath/lib/MoltenVK.xcframework/ios-arm64_x86_64-simulator")
        environment("VK_ICD_FILENAMES", "$iosVulkanSdkPath/share/vulkan/icd.d/MoltenVK_icd.json")
        environment("VK_LAYER_PATH", "$iosVulkanSdkPath/share/vulkan/explicit_layer.d")
    }
    environment("VK_LOADER_DEBUG", "all")

    testLogging {
        showStandardStreams = true
        events(TestLogEvent.PASSED, TestLogEvent.FAILED)
    }
}
