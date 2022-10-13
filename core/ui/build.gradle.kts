import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.apiAccompanistDependencies
import org.michaelbel.moviemade.dependencies.apiCoilDependencies
import org.michaelbel.moviemade.dependencies.apiComposeDependencies
import org.michaelbel.moviemade.dependencies.apiComposeTestDependencies
import org.michaelbel.moviemade.dependencies.apiConstraintLayoutDependencies
import org.michaelbel.moviemade.dependencies.apiCoreSplashScreenDependencies
import org.michaelbel.moviemade.dependencies.apiMaterialDependencies

plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
}

android {
    namespace = namespace("ui")

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

dependencies {
    apiAccompanistDependencies()
    apiComposeDependencies()
    apiConstraintLayoutDependencies()
    apiComposeTestDependencies()
    apiCoreSplashScreenDependencies()
    apiMaterialDependencies()
    apiCoilDependencies()
}