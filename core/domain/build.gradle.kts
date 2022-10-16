plugins {
    id("movies-android-library")
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.domain"
}

dependencies {
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:entities"))
    implementation(project(":core:network"))
    implementation(libs.datastore.core)
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.preferences.core)
}