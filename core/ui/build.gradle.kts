@file:Suppress("UnstableApiUsage")

import org.michaelbel.moviemade.dependencies.KotlinCompilerExtensionVersion
import org.michaelbel.moviemade.dependencies.apiAccompanistDependencies
import org.michaelbel.moviemade.dependencies.apiCoreSplashScreenDependencies
import org.michaelbel.moviemade.dependencies.implementationMaterialDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = KotlinCompilerExtensionVersion
    }
}

dependencies {
    apiAccompanistDependencies()
    apiCoreSplashScreenDependencies()
    implementationMaterialDependencies()
}