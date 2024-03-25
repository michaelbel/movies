import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    id("movies-android-hilt")
}

val tmdbApiKey: String by lazy {
    gradleLocalProperties(rootDir, providers).getProperty("TMDB_API_KEY").orEmpty().ifEmpty {
        System.getenv("TMDB_API_KEY").orEmpty()
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.startup.runtime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.okhttp.logging.interceptor)
            implementation(libs.bundles.retrofit)
            implementation(libs.bundles.ktor)
            implementation(libs.flaker.android.okhttp)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.network_kmp"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
    }

    buildFeatures {
        buildConfig = true
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }

    dependencies {
        implementation(libs.chucker.library) {
            exclude(group = "androidx.constraintlayout")
        }
        debugImplementation(libs.chucker.library) {
            exclude(group = "androidx.constraintlayout")
        }
        releaseImplementation(libs.chucker.library.no.op) {
            exclude(group = "androidx.constraintlayout")
        }
        debugImplementation(libs.flaker.android.okhttp)
        releaseImplementation(libs.flaker.android.okhttp.noop)
    }
}