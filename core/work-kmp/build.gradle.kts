plugins {
    alias(libs.plugins.kotlin.multiplatform)
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

    sourceSets {
        androidMain.dependencies {
            implementation(project(":core:interactor-kmp"))
            implementation(project(":core:common-kmp"))
            implementation(project(":core:network-kmp"))
            implementation(project(":core:notifications-kmp"))
            implementation(project(":core:ui"))
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.paging.compose)
            implementation(libs.androidx.hilt.work)
            implementation(libs.androidx.work.runtime.ktx)
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

    dependencies {
        ksp(libs.androidx.hilt.compiler)
    }
}