@file:Suppress("UnstableApiUsage")

import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.dependencies.OptExperimentalCoroutinesApi
import org.michaelbel.moviemade.dependencies.OptExperimentalFoundationApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.OptExperimentalSerializationApi
import org.michaelbel.moviemade.dependencies.apiActivityDependencies
import org.michaelbel.moviemade.dependencies.apiChuckerDependencies
import org.michaelbel.moviemade.dependencies.apiDataStoreDependencies
import org.michaelbel.moviemade.dependencies.apiFirebaseDependencies
import org.michaelbel.moviemade.dependencies.apiKotlinDependencies
import org.michaelbel.moviemade.dependencies.apiTimberDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies
import org.michaelbel.moviemade.dependencies.implementationRetrofitDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("kapt")
}

android {
    namespace = "org.michaelbel.moviemade.core"
    compileSdk = CompileSdk

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalSerializationApi
        freeCompilerArgs = freeCompilerArgs + OptExperimentalMaterial3Api
        freeCompilerArgs = freeCompilerArgs + OptExperimentalFoundationApi
        freeCompilerArgs = freeCompilerArgs + OptExperimentalCoroutinesApi
    }
}

dependencies {
    api(project(":core:navigation"))
    api(project(":core:ui"))
    apiFirebaseDependencies()
    apiDataStoreDependencies()
    apiChuckerDependencies()
    apiTimberDependencies()
    apiActivityDependencies()
    apiKotlinDependencies()
    implementationHiltDependencies()
    implementationRetrofitDependencies()
}