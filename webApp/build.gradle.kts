@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.nio.charset.StandardCharsets

private val webVersionName = "3.4.0"
private val gitCommitsCount by lazy {
    try {
        val isWindows = System.getProperty("os.name").contains("Windows", ignoreCase = true)
        val processBuilder = when {
            isWindows -> ProcessBuilder("cmd", "/c", "git", "rev-list", "--count", "HEAD")
            else -> ProcessBuilder("git", "rev-list", "--count", "HEAD")
        }
        processBuilder.redirectErrorStream(true)
        processBuilder.start().inputStream.bufferedReader(StandardCharsets.UTF_8).readLine().trim().toInt()
    } catch (_: Exception) {
        1
    }
}

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

abstract class GenerateWebAppInfoTask: DefaultTask() {

    @get:Input
    abstract val versionName: Property<String>

    @get:Input
    abstract val versionCode: Property<Int>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val directory = outputDir.get().asFile
        directory.mkdirs()
        directory.resolve("WebAppInfo.kt").writeText(
            """
            package org.michaelbel.movies

            object WebAppInfo {

                const val VERSION_NAME = "${versionName.get()}"

                const val VERSION_CODE = ${versionCode.get()}L
            }
            """.trimIndent() + "\n"
        )
    }
}

val generateWebAppInfo = tasks.register<GenerateWebAppInfoTask>("generateWebAppInfo") {
    versionName = webVersionName
    versionCode = gitCommitsCount
    outputDir = layout.buildDirectory.dir("generated/webAppInfo/kotlin")
}

kotlin {
    js {
        outputModuleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
        useEsModules()
    }
    wasmJs {
        outputModuleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(project.rootDir.path)
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain {
            kotlin.srcDir(generateWebAppInfo.flatMap { it.outputDir })
        }
        commonMain.dependencies {
            implementation(projects.shared.interactor)
            implementation(projects.shared.platformServices.injectWeb)
            implementation(projects.shared.ui)
            implementation(projects.feature.settings)
            implementation(libs.bundles.jetbrains.compose.material.icons.common)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
