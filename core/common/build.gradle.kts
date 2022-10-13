import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.OptExperimentalCoroutinesApi
import org.michaelbel.moviemade.dependencies.OptExperimentalFoundationApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.apiActivityDependencies
import org.michaelbel.moviemade.dependencies.apiCoreDependencies
import org.michaelbel.moviemade.dependencies.apiFirebaseRemoteConfigDependencies
import org.michaelbel.moviemade.dependencies.apiKotlinDependencies
import org.michaelbel.moviemade.dependencies.apiLifecycleDependencies
import org.michaelbel.moviemade.dependencies.apiPlayCoreDependencies
import org.michaelbel.moviemade.dependencies.apiTimberDependencies
import org.michaelbel.moviemade.dependencies.implementationFirebaseCrashlyticsDependencies
import org.michaelbel.moviemade.dependencies.implementationStartupDependencies

plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
    id("movies-android-hilt")
}

android {
    namespace = namespace("common")

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
            OptExperimentalMaterial3Api,
            OptExperimentalFoundationApi,
            OptExperimentalCoroutinesApi
        )
    }
}

dependencies {
    api(project(":core:entities"))
    api(project(":core:ui"))
    apiCoreDependencies()
    apiTimberDependencies()
    apiActivityDependencies()
    apiKotlinDependencies()
    apiPlayCoreDependencies()
    apiLifecycleDependencies()
    apiFirebaseRemoteConfigDependencies()
    implementationStartupDependencies()
    implementationFirebaseCrashlyticsDependencies()
}