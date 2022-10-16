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
    api(libs.material)
    api(libs.material.compose.theme.adapter)
    api(libs.compose.compiler)
    api(libs.compose.foundation)
    api(libs.compose.foundation.layout)
    api(libs.compose.material3)
    api(libs.compose.runtime)
    api(libs.compose.runtime.livedata)
    api(libs.compose.ui)
    api(libs.compose.ui.tooling)
    api(libs.compose.ui.viewbinding)
    api(libs.accompanist.appcompat.theme)
    api(libs.accompanist.drawablepainter)
    api(libs.accompanist.insets)
    api(libs.accompanist.insets.ui)
    api(libs.accompanist.navigation.animation)
    api(libs.accompanist.systemuicontroller)
}