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

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.jvm.tasks.Jar
import java.io.File
import java.util.*

/**
 * @author shell
 * @since 2022-12-08
 */
internal val Project.libsDir: File
	get() = buildDir.resolve("libs")

internal val Project.proguardDir: File
	get() = libsDir.resolve("proguard")

internal val Project.proguardFile: File
	get() = Optional.of(projectDir.resolve(PRO_GUARD_CONFIGURATION))
		.filter(File::exists)
		.orElseGet { rootDir.resolve(PRO_GUARD_CONFIGURATION) }

internal val Project.runtimeClasspath: Configuration
	get() = configurations.findByName("runtimeClasspath")!!

internal val Project.dependingJarTasks : List<Jar>
	get() = configurations.flatMap(Configuration::getAllDependencies)
		.filterIsInstance(ProjectDependency::class.java)
		.map(ProjectDependency::getDependencyProject)
		.flatMap(Project::getTasks)
		.filterIsInstance(Jar::class.java)

internal val Project.jarTask: Jar
	get() = tasks.findByName("jar") as Jar

internal val Project.jarArtifactName: String
	get() = jarTask.archiveFileName.get()
