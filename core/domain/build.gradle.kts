import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.implementationDataStoreDependencies

plugins {
    id("movies-android-library")
    id("movies-android-hilt")
}

android {
    namespace = namespace("domain")

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
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:entities"))
    implementation(project(":core:network"))
    implementationDataStoreDependencies()
}