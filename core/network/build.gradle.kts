import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.OptExperimentalSerializationApi
import org.michaelbel.moviemade.dependencies.apiRetrofitDependencies
import org.michaelbel.moviemade.dependencies.implementationChuckerDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies
import org.michaelbel.moviemade.dependencies.implementationKotlinxSerializationDependencies
import org.michaelbel.moviemade.dependencies.implementationOkhttpDependencies
import org.michaelbel.moviemade.dependencies.implementationRetrofitConverterSerializationDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("kapt")
}

android {
    namespace = namespace("network")
    compileSdk = CompileSdk

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalSerializationApi
    }
}

dependencies {
    apiRetrofitDependencies()
    implementationHiltDependencies()
    implementationOkhttpDependencies()
    implementationRetrofitConverterSerializationDependencies()
    implementationKotlinxSerializationDependencies()
    implementationChuckerDependencies()
}