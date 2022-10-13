import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.apiGooglePlayServicesAdsDependencies

plugins {
    id("movies-android-library")
}

android {
    namespace = namespace("ads")

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
    apiGooglePlayServicesAdsDependencies()
}