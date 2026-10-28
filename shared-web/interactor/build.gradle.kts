@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    js { browser {} }
    wasmJs { browser {} }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.analytics)
            api(projects.shared.domain)
            api(projects.sharedWeb.repository)
        }
    }
}
