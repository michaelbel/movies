@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()

    sourceSets {
        commonMain.dependencies {
            api(project(":core:platform-services:interactor"))
            implementation(project(":core:notifications"))
        }
        androidMain.dependencies {
            api(libs.bundles.google.firebase.android)
            api(libs.bundles.google.services.android)
            api(libs.bundles.google.play.android)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.platform.gms"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }
}