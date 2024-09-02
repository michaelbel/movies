@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        /*androidMain.dependencies {
            api(libs.bundles.navigation.android)
        }*/
        commonMain.dependencies {
            api(libs.bundles.jetbrains.androidx.navigation.compose.common)
            api(libs.bundles.jetbrains.androidx.core.bundle.common)
        }
        jvmMain.dependencies {
            api(libs.bundles.jetbrains.androidx.lifecycle.viewmodel.compose.common)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.navigation"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }
}