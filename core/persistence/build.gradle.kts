@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.google.ksp)
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":core:common"))
            api(project(":core:network"))
            implementation(libs.bundles.datastore.common)
            implementation(libs.bundles.room.common)
            implementation(libs.bundles.sqlite.common)
            implementation(libs.bundles.okio.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.datastore.android)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.bundles.datastore.desktop)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.persistence"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        buildConfig = true
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}

dependencies {
    add("kspAndroid", libs.bundles.room.compiler.common)
    add("kspDesktop", libs.bundles.room.compiler.common)
}

room {
    schemaDirectory("$projectDir/schemas")
}