plugins {
    id("movies-android-library")
}

android {
    namespace = "org.michaelbel.movies.ads"
}

dependencies {
    api(libs.play.services.ads)
}