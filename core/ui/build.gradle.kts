plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
}

android {
    namespace = "org.michaelbel.movies.ui"
}

dependencies {
    api(libs.androidx.core.splashscreen)
    api(libs.androidx.constraintlayout.compose)
    api(libs.coil.compose)
    api(libs.bundles.material)
    api(libs.bundles.accompanist)
    api(libs.bundles.compose)
}