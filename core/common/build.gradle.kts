@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js {
        browser {}
    }
    wasmJs {
        browser {}
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.kotlinx.coroutines.common)
            api(libs.bundles.kotlinx.datetime.common)
            api(libs.bundles.koin.common)
            api(libs.bundles.napier.common)
            implementation(compose.material3)
        }
        androidMain.dependencies {
            api(libs.bundles.kotlinx.coroutines.android)
            api(libs.bundles.lifecycle.android)
            api(libs.bundles.activity.android)
            api(libs.bundles.biometric.android)
            api(libs.bundles.core.android)
            api(libs.bundles.startup.android)
            api(libs.bundles.work.android)
            api(libs.bundles.timber.android)
            api(libs.bundles.koin.android)
            implementation(libs.bundles.appcompat.android)
            implementation(libs.bundles.browser.android)
            implementation(libs.bundles.paging.android)
        }
        jvmMain.dependencies {
            api(libs.bundles.kotlinx.coroutines.desktop)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.common"
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}