@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    js {
        browser {}
    }
    wasmJs {
        browser {}
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            api(libs.bundles.coil.common)
            api(libs.bundles.jetbrains.androidx.navigation.compose.common)
            api(libs.bundles.jetbrains.androidx.core.bundle.common)
            api(compose.animation)
            api(compose.foundation)
            api(compose.runtime)
            api(compose.runtimeSaveable)
            api(compose.ui)
            api(compose.material)
            api(compose.material3)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}