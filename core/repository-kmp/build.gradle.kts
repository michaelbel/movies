plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
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
            api(project(":core:common-kmp"))
            api(project(":core:network-kmp"))
            api(project(":core:persistence-kmp"))
            implementation(libs.bundles.kotlinx.coroutines.common)
            implementation(libs.bundles.paging.common)
            implementation(libs.bundles.koin.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.koin.android)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.repository_kmp"

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