import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.apiNavigationDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = namespace("navigation")
    compileSdk = CompileSdk

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
}