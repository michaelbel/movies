import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.OptExperimentalCoroutinesApi
import org.michaelbel.moviemade.dependencies.OptExperimentalLifecycleComposeApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterialApi

plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
    id("movies-android-hilt")
}

android {
    namespace = namespace("settings")

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
        freeCompilerArgs = freeCompilerArgs + listOf(
            OptExperimentalMaterialApi,
            OptExperimentalMaterial3Api,
            OptExperimentalCoroutinesApi,
            OptExperimentalLifecycleComposeApi
        )
    }
}

dependencies {
    implementation(project(":core:ads"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
}