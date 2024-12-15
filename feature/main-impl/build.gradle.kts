plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.feature.account)
            api(projects.feature.auth)
            api(projects.feature.details)
            api(projects.feature.feed)
            api(projects.feature.gallery)
            api(projects.feature.search)
            api(projects.feature.settings)
            api(projects.feature.debug)
        }
        jvmMain.dependencies {
            implementation(projects.core.platformServices.injectDesktop)
        }
        iosMain.dependencies {
            implementation(projects.core.platformServices.injectIos)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.main_impl"
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

    buildFeatures {
        buildConfig = true
        compose = true
    }

    val gmsImplementation by configurations
    val hmsImplementation by configurations
    val fossImplementation by configurations
    dependencies {
        gmsImplementation(projects.core.platformServices.injectAndroid)
        hmsImplementation(projects.core.platformServices.injectAndroid)
        fossImplementation(projects.core.platformServices.injectAndroid)
    }
}