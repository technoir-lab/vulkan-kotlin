import io.technoirlab.vfsoverlay.GenerateVfsOverlayTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.HostManager

private val konanDataDir: Provider<File>
    get() = providers.environmentVariable("KONAN_DATA_DIR").map { File(it) }
        .orElse(providers.systemProperty("user.home").map { File(it, ".konan") })
private val androidNdkSysrootDir: Provider<File>
    get() = konanDataDir.map { it.resolve("dependencies/target-toolchain-2-${HostManager.hostOs()}-android_ndk/sysroot") }
private val androidNdkVulkanIncludeDir: Provider<File>
    get() = androidNdkSysrootDir.map { it.resolve("usr/include/vulkan") }
private val vulkanSdkDir: Provider<File>
    get() = providers.environmentVariable("VULKAN_SDK").map { File(it) }
private val vulkanSdkIncludeDir: Provider<File>
    get() = vulkanSdkDir.map { File(it, "${if (HostManager.hostIsMingw) "Include" else "include"}/vulkan") }

pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    val overlayFile = layout.buildDirectory.file("vfsoverlay/vulkan-sdk-overlay.json")
    val generateVfsOverlayTask = tasks.register<GenerateVfsOverlayTask>("generateVfsOverlay") {
        fromDir.set(layout.dir(androidNdkVulkanIncludeDir))
        toDir.set(layout.dir(vulkanSdkIncludeDir))
        outputOverlayFile.set(overlayFile)
    }

    configure<KotlinMultiplatformExtension> {
        targets.withType<KotlinNativeTarget>()
            .matching { it.konanTarget.family == Family.ANDROID }
            .configureEach {
                compilations.configureEach {
                    cinterops.configureEach {
                        tasks.named(interopProcessingTaskName) { dependsOn(generateVfsOverlayTask) }
                        compilerOpts("-ivfsoverlay", overlayFile.get().asFile.absolutePath)
                    }
                }
            }
    }
}
