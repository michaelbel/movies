@Suppress("dsl_scope_violation")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin.android)
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.platform.inject"
    flavorDimensions += "version"

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

    productFlavors {
        create("gms") {
            dimension = "version"
            isDefault = true
        }
        create("hms") {
            dimension = "version"
        }
        create("foss") {
            dimension = "version"
        }
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

val gmsImplementation: Configuration by configurations
val hmsImplementation: Configuration by configurations
val fossImplementation: Configuration by configurations
dependencies {
    gmsImplementation(project(":core:platform-services:gms"))
    hmsImplementation(project(":core:platform-services:hms"))
    fossImplementation(project(":core:platform-services:foss"))
    implementation(project(":core:platform-services:interactor"))
}