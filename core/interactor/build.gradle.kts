@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
}

kotlin {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":core:analytics"))
            api(project(":core:repository"))
            implementation(libs.bundles.room.paging.common)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.interactor"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }
}