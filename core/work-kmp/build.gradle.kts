plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    //alias(libs.plugins.google.ksp)
    id("com.google.devtools.ksp")
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
    androidNativeX64 {
        binaries {
            executable()
        }
    }
    androidNativeArm64 {
        binaries {
            executable()
        }
    }

    sourceSets {
        val androidNativeX64Main by getting
        val androidNativeArm64Main by getting

        androidMain.dependencies {
            implementation(project(":core:interactor-kmp"))
            implementation(project(":core:common-kmp"))
            implementation(project(":core:network-kmp"))
            implementation(project(":core:notifications"))
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
}

dependencies {
    add("kspAndroidNativeX64", libs.androidx.hilt.compiler)
    add("kspAndroidNativeArm64", libs.androidx.hilt.compiler)
}