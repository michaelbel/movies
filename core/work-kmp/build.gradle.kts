plugins {
    alias(libs.plugins.kotlin.multiplatform)
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
            implementation(project(":core:interactor-kmp"))
            implementation(project(":core:common-kmp"))
            implementation(project(":core:network-kmp"))
            implementation(project(":core:notifications-kmp"))
            implementation(project(":core:persistence-kmp"))
            implementation(libs.bundles.koin.common)
        }
        androidMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.paging.compose)
            implementation(libs.androidx.work.runtime.ktx)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.workmanager)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.work_kmp"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}