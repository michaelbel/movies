plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    id("movies-android-hilt")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(compose.material3)
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            api(project(":core:platform-services:interactor-kmp"))
            implementation(project(":core:analytics-kmp"))
            implementation(project(":core:network-kmp"))
            api(libs.kotlinx.coroutines.android)
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
        }
    }
}

android {
    namespace = "org.michaelbel.movies.common_kmp"
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}