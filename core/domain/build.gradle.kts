import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.implementationDataStoreDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("kapt")
}

android {
    namespace = namespace("domain")
    compileSdk = CompileSdk
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core:entities"))
    implementation(project(":core:network"))
    implementationDataStoreDependencies()
    implementationHiltDependencies()
}