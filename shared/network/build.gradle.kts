@file:OptIn(ExperimentalWasmDsl::class)

import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.buildkonfig)
}

private val tmdbApiKey: String by lazy {
    providers.gradleProperty("TMDB_API_KEY")
        .orElse(providers.environmentVariable("TMDB_API_KEY"))
        .orElse(
            providers.provider {
                val localPropertiesFile = rootProject.layout.projectDirectory.file("local.properties").asFile
                when {
                    localPropertiesFile.exists() -> {
                        Properties().apply {
                            localPropertiesFile.inputStream().use(::load)
                        }.getProperty("TMDB_API_KEY").orEmpty()
                    }
                    else -> ""
                }
            }
        )
        .get()
}

private val traktClientId: String by lazy {
    providers.gradleProperty("TRAKT_CLIENT_ID")
        .orElse(providers.environmentVariable("TRAKT_CLIENT_ID"))
        .orElse(
            providers.provider {
                val localPropertiesFile = rootProject.layout.projectDirectory.file("local.properties").asFile
                when {
                    localPropertiesFile.exists() -> {
                        Properties().apply {
                            localPropertiesFile.inputStream().use(::load)
                        }.getProperty("TRAKT_CLIENT_ID").orEmpty()
                    }
                    else -> ""
                }
            }
        )
        .get()
}

kotlin {
    jvm()
    iosArm64()
    iosSimulatorArm64()
    js { browser {} }
    wasmJs { browser {} }

    android {
        namespace = "org.michaelbel.movies.network"
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.common)
            api(libs.bundles.kotlinx.serialization.common)
            implementation(libs.bundles.ktor.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.ktor.android)
            implementation(libs.bundles.startup.android)
            implementation(libs.bundles.okhttp.logging.interceptor.android)
            implementation(libs.bundles.chucker.library.no.op.android)
        }
        jvmMain.dependencies {
            implementation(libs.bundles.ktor.jvm)
        }
        iosMain.dependencies {
            implementation(libs.bundles.ktor.ios)
        }
        webMain.dependencies {
            implementation(libs.bundles.ktor.web)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

buildkonfig {
    packageName = "org.michaelbel.movies.network"

    defaultConfigs {
        buildConfigField(STRING, "TMDB_API_KEY", tmdbApiKey)
        buildConfigField(STRING, "TRAKT_CLIENT_ID", traktClientId)
    }
}
