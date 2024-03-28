plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
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
            api(project(":core:platform-services:interactor-kmp"))
            implementation(project(":core:analytics-kmp"))
            implementation(project(":core:network-kmp"))
            implementation(compose.material3)
            implementation(libs.bundles.kotlinx.coroutines.common)
            implementation(libs.bundles.paging.common)
            implementation(libs.bundles.koin.common)
        }
        androidMain.dependencies {
            api(libs.bundles.kotlinx.coroutines.android)
            api(libs.bundles.lifecycle.android)
            api(libs.androidx.activity.compose)
            api(libs.androidx.biometric.ktx)
            api(libs.androidx.core.ktx)
            api(libs.androidx.startup.runtime)
            api(libs.androidx.work.runtime.ktx)
            api(libs.timber)
            implementation(libs.bundles.androidx.appcompat)
            implementation(libs.androidx.browser)
            implementation(libs.koin.android)
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