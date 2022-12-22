# Gradle ProGuard Plugin

[![Build Status](https://github.com/Scalified/gradle-proguard-plugin/actions/workflows/gradle.yml/badge.svg)](https://github.com/Scalified/gradle-proguard-plugin/actions)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v?label=Plugin&metadataUrl=https://plugins.gradle.org/m2/com/scalified/plugins/gradle/proguard/com.scalified.plugins.gradle.proguard.gradle.plugin/maven-metadata.xml)](https://plugins.gradle.org/plugin/com.scalified.plugins.gradle.proguard)

## Description

[Gradle ProGuard Plugin](https://plugins.gradle.org/plugin/com.scalified.plugins.gradle.proguard) brings easy integration with [Guardsquare ProGuard](https://www.guardsquare.com/manual/home)

## Requirements

* [Gradle 7+](https://gradle.org/)
* [JDK 11+](https://jdk.java.net/11/)

## Changelog

[Changelog](CHANGELOG.md)

## Applying

Build script snippet for plugins DSL for Gradle 2.1 and later:

```gradle
plugins {
  id "com.scalified.plugins.gradle.proguard" version "0.0.1"
}
```

Build script snippet for use in older Gradle versions or where dynamic configuration is required:

```gradle
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "com.scalified:proguard:0.0.1"
  }
}

apply plugin: "com.scalified.plugins.gradle.proguard"
```

## Usage

By default, **ProGuardTask** will be automatically executed after **Jar** task. 
Also, **proguard** task can be executed manually:

```bash
./gradlew proguard
```

## Configuration

**ProGuard Plugin** can be configured via Gradle extension
```groovy
proguard {
    configurations = file("proguard-rules.pro")
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
