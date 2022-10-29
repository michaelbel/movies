plugins {
    id("movies-android-library")
    id("movies-android-hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "org.michaelbel.movies.network"

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
        )
    }
}

dependencies {
    implementation(libs.kotlin.serialization)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.converter.serialization)
    api(libs.retrofit)
    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.library.no.op)
}