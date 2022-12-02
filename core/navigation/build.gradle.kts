plugins {
    id("movies-android-library")
}

android {
    namespace = "org.michaelbel.movies.navigation"
}

dependencies {
    api(libs.androidx.hilt.navigation.compose)
    api(libs.androidx.navigation.compose)
}