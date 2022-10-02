import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.implementationFirebaseAnalyticsDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = namespace("analytics")
    compileSdk = CompileSdk

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementationHiltDependencies()
    implementationFirebaseAnalyticsDependencies()
}