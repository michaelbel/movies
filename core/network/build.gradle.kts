import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("dsl_scope_violation")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    id("movies-android-hilt")
}

val tmdbApiKey: String by lazy {
    gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY").orEmpty().ifEmpty {
        System.getenv("TMDB_API_KEY").orEmpty()
    }
}
val traktClientId: String by lazy {
    gradleLocalProperties(rootDir).getProperty("TRAKT_CLIENT_ID").orEmpty().ifEmpty {
        System.getenv("TRAKT_CLIENT_ID").orEmpty()
    }
}
val traktClientSecret: String by lazy {
    gradleLocalProperties(rootDir).getProperty("TRAKT_CLIENT_SECRET").orEmpty().ifEmpty {
        System.getenv("TRAKT_CLIENT_SECRET").orEmpty()
    }
}

android {
    namespace = "org.michaelbel.movies.network"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        buildConfigField("String", "TRAKT_CLIENT_ID", "\"$traktClientId\"")
        buildConfigField("String", "TRAKT_CLIENT_SECRET", "\"$traktClientSecret\"")
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
    implementation(libs.androidx.startup.runtime)
    api(libs.kotlin.serialization.json)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.converter.serialization)
    api(libs.retrofit)
    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.library.no.op)
    debugImplementation(libs.flaker.android.okhttp)
    releaseImplementation(libs.flaker.android.okhttp.noop)
    // implementation(libs.chucker.library.no.op) enable for benchmark
}