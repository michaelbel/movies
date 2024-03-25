plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    id("movies-android-hilt")
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
            implementation(project(":core:interactor-kmp"))
            implementation(project(":core:common-kmp"))
            implementation(project(":core:ui"))
            implementation(project(":core:ui-kmp"))
            implementation(project(":core:work-kmp"))
            implementation(libs.bundles.androidx.datastore)
            implementation(libs.bundles.androidx.glance)
            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
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

    dependencies {
        ksp(libs.androidx.hilt.compiler)
    }
}