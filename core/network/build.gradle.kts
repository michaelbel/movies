import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.OptExperimentalSerializationApi
import org.michaelbel.moviemade.dependencies.apiRetrofitDependencies
import org.michaelbel.moviemade.dependencies.implementationChuckerDependencies
import org.michaelbel.moviemade.dependencies.implementationKotlinxSerializationDependencies
import org.michaelbel.moviemade.dependencies.implementationOkhttpDependencies
import org.michaelbel.moviemade.dependencies.implementationRetrofitConverterSerializationDependencies

plugins {
    id("movies-android-library")
    id("movies-android-hilt")
    id("kotlinx-serialization")
}

android {
    namespace = namespace("network")

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
    implementationOkhttpDependencies()
    implementationRetrofitConverterSerializationDependencies()
    implementationKotlinxSerializationDependencies()
    implementationChuckerDependencies()
}