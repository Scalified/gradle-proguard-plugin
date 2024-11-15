/*
 * MIT License
 *
 * Copyright (c) 2022 Scalified
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.scalified.plugins.gradle.proguard

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.internal.jvm.Jvm
import org.gradle.kotlin.dsl.*
import org.slf4j.LoggerFactory
import proguard.annotation.Keep
import java.io.File

/**
 * @author shell
 * @since 2022-12-08
 */
class ProGuardPlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger(ProGuardPlugin::class.java)

    override fun apply(project: Project) {
        if (!project.plugins.hasPlugin(JavaPlugin::class)) {
            project.plugins.apply(JavaPlugin::class)
            logger.info("Applied 'JavaPlugin' plugin")
        }

        val extension = project.extensions.create<ProGuardPluginExtension>(PRO_GUARD).apply {
            logger.debug("'$PRO_GUARD' extension created")
            overwriteArtifact.convention(true)
            autoRun.convention(true)
            logger.debug("'{}' extension configured: {}", PRO_GUARD, this)
        }

        val proguardTask = project.tasks.register<ProGuardTask>(PRO_GUARD) {
            logger.debug("'$PRO_GUARD' task registered")
            dependsOn(project.resolveDependingJarTasks())
            outputs.upToDateWhen { false }

            val configurationFiles = extension.configurations.files.ifEmpty { project.proguardFiles }
            configuration(configurationFiles)
            logger.info("Added '{}' configuration files", configurationFiles.map(File::getAbsolutePath))

            val jarArchiveFileName = project.tasks.jar.get().archiveFileName.get()
            injars(project.libsDir.resolve(jarArchiveFileName))
            outjars(project.proguardDir.resolve(jarArchiveFileName))
            libraryjars(
                mapOf("jarfilter" to "!**.jar", "filter" to "!module-info.class"),
                "${Jvm.current().javaHome}/jmods/java.base.jmod"
            )
            libraryjars(File(Keep::class.java.protectionDomain.codeSource.location.toURI()))
            libraryjars(
                mapOf("filter" to "!module-info.class"),
                project.runtimeClasspath.files.distinctBy(File::getName)
            )

            keep("class module-info")
            dontnote("module-info")
            keepattributes("Module*")

            if (extension.overwriteArtifact.get()) {
                doLast {
                    val sourceFile = project.proguardDir.resolve(jarArchiveFileName)
                    val destinationFile = project.libsDir.resolve(jarArchiveFileName)
                    sourceFile.copyTo(destinationFile, overwrite = true)
                    project.proguardDir.deleteRecursively()
                    logger.info("JAR artifact overwritten by '$PRO_GUARD'")
                }
            }
        }

        project.tasks.jar {
            if (extension.autoRun.get()) {
                finalizedBy(proguardTask)
                logger.info("'$PRO_GUARD' autorun enabled after the 'Jar' task")
            }
        }
    }

}
