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
            api(projects.shared.common)
            api(projects.shared.persistence)
            api(libs.bundles.jetbrains.compose.material3.common)
            api(libs.bundles.jetbrains.compose.foundation.common)
            api(libs.bundles.jetbrains.compose.runtime.common)
            api(libs.bundles.jetbrains.compose.foundation.common)
            implementation(libs.bundles.coil.common)
        }
    }
}
