@file:Suppress("UnstableApiUsage")

import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.dependencies.apiGooglePlayServicesAdsDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "org.michaelbel.moviemade.ads"
    compileSdk = CompileSdk
}

dependencies {
    apiGooglePlayServicesAdsDependencies()
}