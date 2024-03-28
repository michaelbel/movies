plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
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
            implementation(project(":core:interactor-kmp"))
            implementation(project(":core:common-kmp"))
            implementation(project(":core:ui-kmp"))
            implementation(project(":core:work-kmp"))
            implementation(libs.bundles.datastore.common)
            implementation(libs.bundles.koin.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.datastore.android)
            implementation(libs.bundles.androidx.glance)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.bundles.koin.android)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.widget_kmp"
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}