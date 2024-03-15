@Suppress("dsl_scope_violation")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin.android)
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.gallery_impl"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    /*buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            initWith(getByName("release"))
        }
    }*/

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
        )
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
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
    api(project(":core:navigation"))
    api(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":core:interactor"))
    implementation(project(":core:network"))
    implementation(project(":core:work"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.test.espresso)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.benchmark.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    lintChecks(libs.lint.checks)
}