import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("kapt")
}

android {
    namespace = "org.michaelbel.movies.entities"
    compileSdk = CompileSdk
}

dependencies {
    implementation(project(":core:network"))
    implementationHiltDependencies()
}