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
        androidMain.dependencies {
            api(libs.bundles.navigation.android)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            api(libs.bundles.compose.navigation.common)
            api(libs.bundles.compose.viewmodel.common)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.navigation"

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