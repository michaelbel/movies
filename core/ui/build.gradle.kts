@Suppress("dsl_scope_violation")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.michaelbel.movies.ui"

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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
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
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:persistence"))
    implementation(project(":core:ui-kmp"))
    api(libs.androidx.core.splashscreen)
    api(libs.androidx.constraintlayout.compose)
    api(libs.androidx.palette.ktx)
    api(libs.coil.compose)
    api(libs.bundles.androidx.compose)
    api(libs.bundles.google.material)
    api(libs.androidx.paging.compose)
    lintChecks(libs.lint.checks)
}