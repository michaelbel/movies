plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
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
            api(libs.bundles.koin.common)
            implementation(compose.material3)
            implementation(libs.bundles.kotlinx.coroutines.common)
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
            api(libs.bundles.koin.compose.work.android)
            implementation(libs.bundles.appcompat.android)
            implementation(libs.bundles.browser.android)
            implementation(libs.bundles.paging.common) // fixme ломает navigation
        }
        val desktopMain by getting
        desktopMain.dependencies {
            api(libs.bundles.kotlinx.coroutines.desktop)
            api(libs.bundles.jetbrains.androidx.lifecycle.viewmodel.compose.common)
        }
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

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}