/*
 * MIT License
 *
 * Copyright (c) 2019 Scalified
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
 *
 */
plugins {
	`kotlin-dsl`

	id("com.gradle.plugin-publish") version "1.1.0"
}

gradlePlugin {
	plugins {
		create("ProGuard Plugin") {
			id = "com.scalified.plugins.gradle.proguard"
			displayName = "${project.properties["PROJECT_NAME"]}"
			description = "${project.properties["PROJECT_DESCRIPTION"]}"
			implementationClass = "com.scalified.plugins.gradle.proguard.ProGuardPlugin"
			version = project.version
			website.set("https://scalified.com/")
			vcsUrl.set("${project.properties["PROJECT_URL"]}")
			tags.set(listOf("proguard", "obfuscate"))
		}
	}
}

dependencies {
	compileOnly(kotlin("gradle-plugin"))

	implementation("com.guardsquare:proguard-gradle:7.3.1")
	implementation("com.guardsquare:proguard-annotations:7.3.1")
}
