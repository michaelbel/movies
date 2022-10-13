import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.michaelbel.moviemade.App.namespace

plugins {
    id("movies-android-library")
    id("movies-android-hilt")
}

val localTmdbApiKey: String? by lazy {
    gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY")
}

val remoteTmdbApiKey: String by lazy {
    System.getenv("TMDB_API_KEY").orEmpty()
}

val tmdbApiKey: String by lazy {
    if (localTmdbApiKey != null) localTmdbApiKey.orEmpty() else remoteTmdbApiKey
}

android {
    namespace = namespace("entities")

    defaultConfig {
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core:network"))
}