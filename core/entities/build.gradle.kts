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
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}

dependencies {
    implementation(project(":core:network"))
}