@file:Suppress("UnstableApiUsage")

import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.dependencies.OptExperimentalSerializationApi
import org.michaelbel.moviemade.dependencies.apiFirebaseDependencies
import org.michaelbel.moviemade.dependencies.implementationChuckerDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies
import org.michaelbel.moviemade.dependencies.implementationKotlinDependencies
import org.michaelbel.moviemade.dependencies.implementationRetrofitDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("kapt")
}

android {
    compileSdk = CompileSdk

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalSerializationApi
    }
}

dependencies {
    api(project(":core:navigation"))
    api(project(":core:ui"))
    apiFirebaseDependencies()
    implementationKotlinDependencies()
    implementationHiltDependencies()
    implementationRetrofitDependencies()
    implementationChuckerDependencies()
}