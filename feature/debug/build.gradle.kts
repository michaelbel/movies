@file:OptIn(ExperimentalWasmDsl::class, ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate

plugins {
    alias(libs.plugins.kotlin.multiplatform)
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
        namespace = "org.michaelbel.movies.debug"
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        androidResources.enable = true
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.ui)
            implementation(projects.shared.platformServices.interactor)
            implementation(projects.shared.interactor)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
}
