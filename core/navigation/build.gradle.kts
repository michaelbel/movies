import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.apiHiltNavigationDependencies
import org.michaelbel.moviemade.dependencies.apiNavigationDependencies

plugins {
    id("movies-android-library")
}

android {
    namespace = namespace("navigation")

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile(
                "proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    apiNavigationDependencies()
    apiHiltNavigationDependencies()
}