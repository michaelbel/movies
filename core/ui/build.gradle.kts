plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
}

android {
    namespace = "org.michaelbel.movies.ui"
}

dependencies {
    api(libs.core.splashscreen)
    api(libs.constraintlayout.compose)
    api(libs.coil.compose)
    api(libs.bundles.material)
    api(libs.compose.compiler)
    api(libs.compose.foundation)
    api(libs.compose.foundation.layout)
    api(libs.compose.material3)
    api(libs.compose.runtime)
    api(libs.compose.runtime.livedata)
    api(libs.compose.ui)
    api(libs.compose.ui.tooling)
    api(libs.compose.ui.viewbinding)
    api(libs.bundles.accompanist)
}