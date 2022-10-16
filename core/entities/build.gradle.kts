import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("movies-android-library")
    id("movies-android-hilt")
}

val tmdbApiKey: String by lazy {
    gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY").orEmpty().ifEmpty {
        System.getenv("TMDB_API_KEY").orEmpty()
    }
}

android {
    namespace = "org.michaelbel.movies.entities"

    defaultConfig {
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
    }
}

dependencies {
    implementation(project(":core:network"))
}