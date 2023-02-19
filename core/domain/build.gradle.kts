plugins {
    id("movies-android-library")
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.domain"

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

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}

dependencies {
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:entities"))
    implementation(project(":core:network"))
    implementation(libs.bundles.datastore)
    implementation(libs.bundles.room)
    kapt(libs.androidx.room.compiler)
}