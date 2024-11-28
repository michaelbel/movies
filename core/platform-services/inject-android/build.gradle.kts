plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()

    sourceSets {
        commonMain.dependencies {
            api(project(":core:platform-services:interactor"))
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.platform.inject_android"
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

    val gmsImplementation by configurations
    val hmsImplementation by configurations
    val fossImplementation by configurations
    dependencies {
        gmsImplementation(project(":core:platform-services:gms"))
        hmsImplementation(project(":core:platform-services:hms"))
        fossImplementation(project(":core:platform-services:foss"))
    }
}