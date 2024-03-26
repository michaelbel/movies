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
            implementation(project(":core:platform-services:interactor-kmp"))
        }
    }
}

android {
    namespace = "org.michaelbel.movies.platform.inject_kmp"
    flavorDimensions += "version"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    productFlavors {
        create("gms") {
            dimension = "version"
            isDefault = true
        }
        create("hms") {
            dimension = "version"
        }
        create("foss") {
            dimension = "version"
        }
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }

    val gmsImplementation by configurations
    val hmsImplementation by configurations
    val fossImplementation by configurations
    dependencies {
        gmsImplementation(project(":core:platform-services:gms-kmp"))
        hmsImplementation(project(":core:platform-services:hms-kmp"))
        fossImplementation(project(":core:platform-services:foss-kmp"))
    }
}