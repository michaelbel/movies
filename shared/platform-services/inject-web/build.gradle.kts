@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    js { browser {} }
    wasmJs { browser {} }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.platformServices.foss)
        }
    }
}
