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
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.named
import java.io.File

/**
 * @author shell
 * @since 2022-12-08
 */
internal val Project.libsDir: File
    get() = layout.buildDirectory.dir("libs").get().asFile

internal val Project.proguardDir: File
    get() = libsDir.resolve(PRO_GUARD)

internal val Project.proguardFiles: List<File>
    get() = listOf(rootDir, projectDir)
        .map { it.resolve(PRO_GUARD_CONFIGURATION) }
        .filter(File::exists)

internal val Project.runtimeClasspath: Configuration
    get() = configurations.getByName("runtimeClasspath")

internal val Project.dependingProjects: List<Project>
    get() = configurations.flatMap(Configuration::getAllDependencies)
        .filterIsInstance<ProjectDependency>()
        .map { projectDependency -> project.project(projectDependency.path) }
        .distinctBy(Project::getName)

internal val TaskContainer.jar: TaskProvider<Jar>
    get() = named<Jar>("jar")

internal fun Project.resolveDependingJarTasks(): List<Jar> {
    tailrec fun resolve(projects: List<Project>, acc: List<Project> = emptyList()): List<Project> {
        if (projects.isEmpty()) return acc.distinctBy(Project::getName)
        val first = projects.first()
        val next = first.dependingProjects
        return resolve(next + projects.subList(1, projects.size), acc + first)
    }

    return resolve(this.dependingProjects).flatMap(Project::getTasks)
        .filterIsInstance<Jar>()
        .distinct()
}
