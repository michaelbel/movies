plugins {
    alias(libs.plugins.dynamic.feature)
    alias(libs.plugins.kotlin)
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
        /*create("foss") {
            dimension = "version"
        }
        create("hms") {
            dimension = "version"
        }*/
        create("gms") {
            dimension = "version"
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":android-app"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3)
    implementation(libs.gms.play.services.instantapps)
}