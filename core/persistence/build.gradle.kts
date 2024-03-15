@Suppress("dsl_scope_violation")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin)
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.persistence"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
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
    implementation(project(":core:network"))
    implementation(libs.bundles.datastore)
    api(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
}