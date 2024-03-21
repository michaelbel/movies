plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    id("movies-android-hilt")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            api(project(":core:platform-services:interactor"))
            implementation(project(":core:analytics-kmp"))
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