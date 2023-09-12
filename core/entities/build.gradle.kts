import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("dsl_scope_violation")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin)
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

    /*buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            initWith(getByName("release"))
        }
    }*/

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}