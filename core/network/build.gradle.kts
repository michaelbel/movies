import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.dependencies.OptExperimentalSerializationApi
import org.michaelbel.moviemade.dependencies.implementationChuckerDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies
import org.michaelbel.moviemade.dependencies.implementationKotlinxSerializationDependencies
import org.michaelbel.moviemade.dependencies.implementationOkhttpDependencies
import org.michaelbel.moviemade.dependencies.implementationRetrofitDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("kapt")
}

android {
    namespace = "org.michaelbel.movies.network"
    compileSdk = CompileSdk

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalSerializationApi
    }
}

dependencies {
    implementationHiltDependencies()
    implementationOkhttpDependencies()
    implementationRetrofitDependencies()
    implementationKotlinxSerializationDependencies()
    implementationChuckerDependencies()
}