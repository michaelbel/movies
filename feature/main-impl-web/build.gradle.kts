@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    js { browser() }
    wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            api(projects.feature.feedWeb)
            api(libs.bundles.androidx.navigation3)
            api(libs.bundles.koin.common)
            implementation(compose.material3)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}