@Suppress("dsl_scope_violation")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.common"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    /*buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            initWith(getByName("release"))
        }
    }*/

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
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
    api(project(":core:platform-services:interactor"))
    implementation(project(":core:analytics"))
    implementation(project(":core:network"))
    api(libs.bundles.kotlinx.coroutines)
    api(libs.androidx.activity.compose)
    api(libs.androidx.biometric.ktx)
    api(libs.androidx.core.ktx)
    api(libs.androidx.paging.compose)
    api(libs.androidx.startup.runtime)
    api(libs.androidx.work.runtime.ktx)
    api(libs.androidx.hilt.work)
    api(libs.bundles.androidx.lifecycle)
    api(libs.timber)
    implementation(libs.bundles.androidx.appcompat)
    implementation(libs.androidx.browser)
    lintChecks(libs.lint.checks)
}