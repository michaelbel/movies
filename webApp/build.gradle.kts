@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
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
        commonMain.dependencies {
            implementation(projects.shared.analytics)
            implementation(projects.shared.common)
            implementation(projects.shared.domain)
            implementation(projects.shared.persistence)
            implementation(projects.shared.platformServices.foss)
            implementation(libs.bundles.jetbrains.compose.animation.common)
            implementation(libs.bundles.jetbrains.compose.components.common)
            implementation(libs.bundles.jetbrains.compose.foundation.common)
            implementation(libs.bundles.jetbrains.compose.runtime.common)
            implementation(libs.bundles.jetbrains.compose.material3.common)
            implementation(libs.bundles.jetbrains.compose.material.icons.common)
            implementation(libs.bundles.jetbrains.compose.ui.common)
            implementation(libs.bundles.coil.common)
            implementation(libs.bundles.koin.common)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
