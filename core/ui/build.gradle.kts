@file:Suppress("UnstableApiUsage")

import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.dependencies.KotlinCompilerExtensionVersion
import org.michaelbel.moviemade.dependencies.apiAccompanistDependencies
import org.michaelbel.moviemade.dependencies.apiCoilDependencies
import org.michaelbel.moviemade.dependencies.apiComposeDependencies
import org.michaelbel.moviemade.dependencies.apiComposeTestDependencies
import org.michaelbel.moviemade.dependencies.apiCoreSplashScreenDependencies
import org.michaelbel.moviemade.dependencies.apiMaterialDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = CompileSdk

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = KotlinCompilerExtensionVersion
    }
}

dependencies {
    apiAccompanistDependencies()
    apiComposeDependencies()
    apiComposeTestDependencies()
    apiCoreSplashScreenDependencies()
    apiMaterialDependencies()
    apiCoilDependencies()
}