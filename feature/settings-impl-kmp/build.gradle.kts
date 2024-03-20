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
            implementation(project(":core:platform-services:interactor"))
            implementation(project(":core:widget"))
            api(project(":core:navigation"))
            api(project(":core:common-kmp"))
            api(project(":core:ui"))
            api(project(":core:ui-kmp"))
            implementation(project(":core:interactor-kmp"))
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