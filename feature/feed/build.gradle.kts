import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.OptExperimentalLifecycleComposeApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.implementationPagingDependencies

plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
    id("movies-android-hilt")
}

android {
    namespace = namespace("feed")

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
            OptExperimentalLifecycleComposeApi
        )
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
    implementationPagingDependencies()
}