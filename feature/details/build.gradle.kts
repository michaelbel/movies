plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.details"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-Xopt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}

dependencies {
    implementation(project(":core:ads"))
    implementation(project(":core:common"))
    implementation(project(":core:navigation"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
}