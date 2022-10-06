import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.MinSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    kotlin("kapt")
}

val localTmdbApiKey: String by lazy {
    gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY")
}

val remoteTmdbApiKey: String by lazy {
    System.getenv("TMDB_API_KEY").orEmpty()
}

val tmdbApiKey: String by lazy {
    if (localTmdbApiKey != "null") localTmdbApiKey else remoteTmdbApiKey
}

android {
    namespace = namespace("entities")
    compileSdk = CompileSdk

    defaultConfig {
        minSdk = MinSdk
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":core:network"))
    implementationHiltDependencies()
}