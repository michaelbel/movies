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
            api(projects.sharedWeb.interactor)
            implementation(projects.shared.domain)
            implementation(projects.sharedWeb.ui)
            implementation(libs.bundles.jetbrains.compose.foundation.common)
            implementation(libs.bundles.jetbrains.compose.material.icons.common)
            implementation(libs.bundles.jetbrains.compose.material3.common)
            implementation(libs.bundles.jetbrains.compose.runtime.common)
            implementation(libs.bundles.jetbrains.compose.ui.common)
            implementation(libs.bundles.coil.common)
            implementation(libs.bundles.koin.common)
        }
    }
}
