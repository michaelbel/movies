plugins {
    id("movies-android-library")
}

android {
    namespace = "org.michaelbel.movies.ads"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }
}

dependencies {
    api(libs.play.services.ads)
}