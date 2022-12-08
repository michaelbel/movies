plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
}

android {
    namespace = "org.michaelbel.movies.ui"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.get()
    }
}

dependencies {
    api(libs.androidx.core.splashscreen)
    api(libs.androidx.constraintlayout.compose)
    api(libs.coil.compose)
    api(libs.bundles.material)
    api(libs.bundles.accompanist)
    api(libs.bundles.compose)
}