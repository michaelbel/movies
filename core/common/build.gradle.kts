plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.common"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}

dependencies {
    api(project(":core:entities"))
    api(project(":core:ui"))
    api(libs.bundles.kotlin.coroutines)
    api(libs.firebase.config)
    api(libs.play.services.base)
    api(libs.play.core)
    api(libs.core)
    api(libs.activity.compose)
    api(libs.bundles.lifecycle)
    api(libs.timber)
    implementation(libs.firebase.crashlytics)
    implementation(libs.startup.runtime)
    testApi(libs.kotlin.coroutines.test)
    androidTestApi(libs.kotlin.coroutines.test)
}