
import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.OptExperimentalCoroutinesApi
import org.michaelbel.moviemade.dependencies.OptExperimentalFoundationApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.apiActivityDependencies
import org.michaelbel.moviemade.dependencies.apiFirebaseDependencies
import org.michaelbel.moviemade.dependencies.apiKotlinDependencies
import org.michaelbel.moviemade.dependencies.apiTimberDependencies
import org.michaelbel.moviemade.dependencies.implementationFirebaseCrashlyticsDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies
import org.michaelbel.moviemade.dependencies.implementationStartupDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("kapt")
}

android {
    namespace = namespace("core")
    compileSdk = CompileSdk

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalMaterial3Api
        freeCompilerArgs = freeCompilerArgs + OptExperimentalFoundationApi
        freeCompilerArgs = freeCompilerArgs + OptExperimentalCoroutinesApi
    }
}

dependencies {
    api(project(":core:entities"))
    api(project(":core:navigation"))
    api(project(":core:ui"))
    apiFirebaseDependencies()
    apiTimberDependencies()
    apiActivityDependencies()
    apiKotlinDependencies()
    implementationHiltDependencies()
    implementationStartupDependencies()
    implementationFirebaseCrashlyticsDependencies()
}