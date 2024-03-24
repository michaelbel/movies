plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
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

    sourceSets {
        androidMain.dependencies {
            implementation(project(":core:platform-services:interactor"))
            implementation(project(":core:network-kmp"))
            api(project(":core:analytics-kmp"))
            api(project(":core:common-kmp"))
            api(project(":core:persistence-kmp"))
            api(project(":core:repository-kmp"))
        }
    }
}

android {
    namespace = "org.michaelbel.movies.interactor_kmp"

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