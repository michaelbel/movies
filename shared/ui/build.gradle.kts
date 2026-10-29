@file:OptIn(ExperimentalWasmDsl::class, ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    jvm()
    iosArm64()
    iosSimulatorArm64()
    js { browser {} }
    wasmJs { browser {} }

    applyHierarchyTemplate(KotlinHierarchyTemplate.default) {
        common {
            group("web") {
                withJs()
                withWasmJs()
            }
        }
    }

    android {
        namespace = "org.michaelbel.movies.ui"
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        androidResources.enable = true
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.persistence)
            api(libs.bundles.coil.common)
            api(libs.bundles.jetbrains.androidx.lifecycle.common)
            api(libs.bundles.jetbrains.androidx.navigation3.common)
            api(libs.bundles.jetbrains.androidx.core.common)
            api(libs.bundles.jetbrains.compose.animation.common)
            api(libs.bundles.jetbrains.compose.components.common)
            api(libs.bundles.jetbrains.compose.foundation.common)
            api(libs.bundles.jetbrains.compose.material.common)
            api(libs.bundles.jetbrains.compose.material3.common)
            api(libs.bundles.jetbrains.compose.runtime.common)
            api(libs.bundles.jetbrains.compose.runtime.saveable.common)
            api(libs.bundles.jetbrains.compose.ui.common)
            api(libs.bundles.jetbrains.compose.ui.tooling.common)
            implementation(libs.bundles.jetbrains.compose.material.icons.common)
        }
        androidMain.dependencies {
            api(libs.bundles.core.splashscreen.android)
            api(libs.bundles.compose.android)
            api(libs.bundles.google.material.android)
        }
        jvmMain.dependencies {
            api(compose.desktop.currentOs)
            api(libs.bundles.compose.desktop)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}
