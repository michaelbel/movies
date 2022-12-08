plugins {
    id("movies-android-library")
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.analytics"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }
}

dependencies {
    implementation(libs.firebase.analytics)
}