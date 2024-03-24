plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
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
            implementation(project(":core:navigation-kmp"))
        }
        androidMain.dependencies {
            implementation(project(":core:ui-kmp"))
            implementation(project(":feature:settings-impl-kmp"))
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(project(":feature:settings-impl-kmp"))
            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.common)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(libs.precompose)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.settings_kmp"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
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