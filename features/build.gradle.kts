import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.namespace

plugins {
    id("com.android.library")
}

android {
    namespace = namespace("features")
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
}