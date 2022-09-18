@file:Suppress("UnstableApiUsage")

import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.dependencies.apiNavigationDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "org.michaelbel.moviemade.navigation"
    compileSdk = CompileSdk
}

dependencies {
    apiNavigationDependencies()
}