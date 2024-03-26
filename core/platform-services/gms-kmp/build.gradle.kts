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

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.koin.common)
        }
        androidMain.dependencies {
            implementation(project(":core:platform-services:interactor-kmp"))
            implementation(project(":core:notifications-kmp"))
            api(libs.bundles.google.firebase)
            api(libs.bundles.google.services)
            api(libs.google.play.core.ktx)
            implementation(libs.koin.android)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.platform.gms_kmp"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

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