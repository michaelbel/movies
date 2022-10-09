import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.MinSdk
import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.KotlinCompilerExtensionVersion
import org.michaelbel.moviemade.dependencies.apiAccompanistDependencies
import org.michaelbel.moviemade.dependencies.apiCoilDependencies
import org.michaelbel.moviemade.dependencies.apiComposeDependencies
import org.michaelbel.moviemade.dependencies.apiComposeTestDependencies
import org.michaelbel.moviemade.dependencies.apiConstraintLayoutDependencies
import org.michaelbel.moviemade.dependencies.apiCoreSplashScreenDependencies
import org.michaelbel.moviemade.dependencies.apiMaterialDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = namespace("ui")
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = KotlinCompilerExtensionVersion
    }
}

dependencies {
    apiAccompanistDependencies()
    apiComposeDependencies()
    apiConstraintLayoutDependencies()
    apiComposeTestDependencies()
    apiCoreSplashScreenDependencies()
    apiMaterialDependencies()
    apiCoilDependencies()
}