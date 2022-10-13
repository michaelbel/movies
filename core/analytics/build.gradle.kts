import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.implementationFirebaseAnalyticsDependencies

plugins {
    id("movies-android-library")
    id("movies-android-hilt")
}

android {
    namespace = namespace("analytics")

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementationFirebaseAnalyticsDependencies()
}