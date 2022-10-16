plugins {
    id("movies-android-library")
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.analytics"
}

dependencies {
    implementation(libs.firebase.analytics)
}