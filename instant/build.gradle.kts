plugins {
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.dynamic.feature)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.michaelbel.movies.instant"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    flavorDimensions += "version"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
    }

    buildFeatures {
        compose = true
    }

    productFlavors {
        /*create("foss") { // todo Uncomment to create a signed release
            dimension = "version"
        }
        create("hms") {
            dimension = "version"
        }*/
        create("gms") {
            dimension = "version"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(projects.androidApp)
    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(libs.bundles.appcompat.android)
    implementation(libs.bundles.compose.android)
    implementation(libs.bundles.google.services.instantapps.android)
}