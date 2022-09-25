import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.apiGooglePlayServicesAdsDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = namespace("ads")
    compileSdk = CompileSdk
}

dependencies {
    apiGooglePlayServicesAdsDependencies()
}