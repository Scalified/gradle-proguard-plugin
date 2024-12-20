# Gradle ProGuard Plugin

[![Build Status](https://github.com/Scalified/gradle-proguard-plugin/actions/workflows/build.yml/badge.svg)](https://github.com/Scalified/gradle-proguard-plugin/actions)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?label=plugin&metadataUrl=https://plugins.gradle.org/m2/com/scalified/plugins/gradle/proguard/com.scalified.plugins.gradle.proguard.gradle.plugin/maven-metadata.xml)](https://plugins.gradle.org/plugin/com.scalified.plugins.gradle.proguard)

## Description

[Gradle ProGuard Plugin](https://plugins.gradle.org/plugin/com.scalified.plugins.gradle.proguard) brings easy integration with [Guardsquare ProGuard](https://www.guardsquare.com/manual/home)

## Requirements

* [Gradle 8+](https://gradle.org/)

## Usage

```kotlin
plugins {
    id("com.scalified.plugins.gradle.proguard") version "$version"
}
```

By default, **ProGuardTask** will be automatically executed after **Jar** task.
Also, **proguard** task can be executed manually:

```bash
./gradlew proguard
```

### ProGuard Annotations

[**ProGuard** annotations](https://github.com/Guardsquare/proguard/tree/master/annotations) supported out-of-the-box.
Just add the **ProGuard** annotations dependency:

```kotlin
compileOnly("com.guardsquare:proguard-annotations:$version")
```

## Configuration

**ProGuard Plugin** can be configured via Gradle extension

```kotlin
proguard {
    configurations.setFrom(files("$rootDir/proguard-root-rules.pro", "$projectDir/proguard-project-rules.pro"))
    // ...
}
```

By default, **ProGuard Plugin** will use **proguard-rules.pro** files located in the root directory and the project directory

### List of supported configuration parameters

| Name                  | Description                                                                 |     Default Value      |
|:----------------------|:----------------------------------------------------------------------------|:----------------------:|
| **configurations**    | **ProGuard** configuration file name                                        | **proguard-rules.pro** |
| **overwriteArtifact** | If enabled, jar artifact will be overwritten by **ProGuard** obfuscated jar |        **true**        |
| **autoRun**           | If enabled, jar task will be finalized by proguard task                     |        **true**        |

## License

```
MIT License

Copyright (c) 2022 Scalified

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Scalified Links

* [Scalified](http://www.scalified.com)
* [Scalified Official Facebook Page](https://www.facebook.com/scalified)
* <a href="mailto:info@scalified.com?subject=[Gradle ProGuard Plugin]: Proposals And Suggestions">Scalified Support</a>
