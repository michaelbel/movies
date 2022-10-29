plugins {
    id("movies-android-library")
}

android {
    namespace = "org.michaelbel.movies.navigation"
}

dependencies {
    api(libs.hilt.navigation.compose)
    api(libs.navigation.compose)
}