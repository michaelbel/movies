@file:Suppress("UnstableApiUsage")

import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.dependencies.KotlinCompilerExtensionVersion
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.implementationAccompanistDependencies
import org.michaelbel.moviemade.dependencies.implementationCoilDependencies
import org.michaelbel.moviemade.dependencies.implementationComposeDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies
import org.michaelbel.moviemade.dependencies.implementationPagingDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = CompileSdk

    composeOptions {
        kotlinCompilerExtensionVersion = KotlinCompilerExtensionVersion
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalMaterial3Api
    }
}

dependencies {
    implementation(project(":core"))
    implementationHiltDependencies()
    implementationPagingDependencies()
    implementationComposeDependencies()
    implementationAccompanistDependencies()
    implementationCoilDependencies()
}