@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.buildkonfig)
}

private val tmdbApiKey: String by lazy {
    gradleLocalProperties(rootDir, providers).getProperty("TMDB_API_KEY").orEmpty().ifEmpty {
        System.getenv("TMDB_API_KEY").orEmpty()
    }
}

kotlin {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":core:common"))
            api(libs.bundles.kotlinx.serialization.common)
            implementation(libs.bundles.ktor.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.ktor.android)
            implementation(libs.bundles.startup.android)
            implementation(libs.bundles.okhttp.logging.interceptor.android)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.network"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
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

    dependencies {
        debugImplementation(libs.bundles.chucker.library.android) {
            exclude(group = "androidx.constraintlayout")
        }
        releaseImplementation(libs.bundles.chucker.library.no.op.android) {
            exclude(group = "androidx.constraintlayout")
        }
        debugImplementation(libs.bundles.flaker.android)
        releaseImplementation(libs.bundles.flaker.noop.android)
    }
}

buildkonfig {
    packageName = "org.michaelbel.movies.network"

    defaultConfigs {
        buildConfigField(STRING, "TMDB_API_KEY", tmdbApiKey)
    }
}