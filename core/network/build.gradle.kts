plugins {
    id("movies-android-library")
    id("movies-android-hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "org.michaelbel.movies.network"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
        )
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
    implementation(libs.kotlin.serialization)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.converter.serialization)
    api(libs.retrofit)
    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.library.no.op)
}