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
                jvmTarget = "1.8"
            }
        }
    }
    jvm("desktop")

    sourceSets {
        androidMain.dependencies {
            implementation(project(":core:platform-services:interactor"))
            implementation(project(":core:widget"))
            api(project(":core:navigation-kmp"))
            api(project(":core:common-kmp"))
            api(project(":core:ui"))
            api(project(":core:ui-kmp"))
            implementation(project(":core:interactor-kmp"))
        }
        val desktopMain by getting
        desktopMain.dependencies {
            api(project(":core:common-kmp"))
            api(project(":core:ui-kmp"))
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
    namespace = "org.michaelbel.movies.settings_impl_kmp"

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