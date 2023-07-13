@Suppress("dsl_scope_violation")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.network"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            initWith(getByName("release"))
        }
    }

    buildFeatures {
        buildConfig = true
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
        )
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

dependencies {
    api(libs.kotlin.serialization.json)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.converter.serialization)
    api(libs.retrofit)
    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.library.no.op)
    // implementation(libs.chucker.library.no.op) enable for benchmark
}