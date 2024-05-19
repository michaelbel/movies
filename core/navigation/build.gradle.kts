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
        /*androidMain.dependencies {
            api(libs.bundles.navigation.android)
        }*/
        commonMain.dependencies {
            api(libs.bundles.jetbrains.androidx.navigation.compose.common)
            api(libs.bundles.jetbrains.androidx.core.bundle.common)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            api(libs.bundles.jetbrains.androidx.lifecycle.viewmodel.compose.common)
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