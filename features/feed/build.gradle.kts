import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.MinSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.KotlinCompilerExtensionVersion
import org.michaelbel.moviemade.dependencies.OptExperimentalLifecycleComposeApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies
import org.michaelbel.moviemade.dependencies.implementationPagingDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = namespace("feed")
    compileSdk = CompileSdk

    defaultConfig {
        minSdk = MinSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = KotlinCompilerExtensionVersion
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalMaterial3Api
        freeCompilerArgs = freeCompilerArgs + OptExperimentalLifecycleComposeApi
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core:analytics"))
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
    implementationHiltDependencies()
    implementationPagingDependencies()
}