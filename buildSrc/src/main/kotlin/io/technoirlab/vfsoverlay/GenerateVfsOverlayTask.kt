package io.technoirlab.vfsoverlay

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

@DisableCachingByDefault
abstract class GenerateVfsOverlayTask : DefaultTask() {
    @get:Internal
    abstract val fromDir: DirectoryProperty

    @get:Internal
    abstract val toDir: DirectoryProperty

    @get:OutputFile
    abstract val outputOverlayFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val json = """
            {
              "version": 0,
              "roots": [
                { "name": "${fromDir.get().asFile.normalizedPath()}",
                  "type": "directory-remap",
                  "external-contents": "${toDir.get().asFile.normalizedPath()}"
                }
              ]
            }
        """.trimIndent()
        val out = outputOverlayFile.get().asFile
        out.parentFile.mkdirs()
        out.writeText(json)
    }

    private fun File.normalizedPath(): String =
        absolutePath.replace("\\", "/")
}
