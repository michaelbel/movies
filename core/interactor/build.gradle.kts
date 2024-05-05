plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            api(project(":core:analytics"))
            api(project(":core:repository"))
            implementation(compose.runtime)
            implementation(compose.runtimeSaveable)
            implementation(libs.bundles.kotlinx.coroutines.common)
            implementation(libs.bundles.room.paging.common)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.interactor"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}