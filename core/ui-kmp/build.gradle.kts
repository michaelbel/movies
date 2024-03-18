plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targets.all {
        compilations.all {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(project(":core:common"))
            implementation(project(":core:network"))
            implementation(project(":core:persistence"))
            api(libs.androidx.core.splashscreen)
            api(libs.androidx.constraintlayout.compose)
            api(libs.androidx.palette.ktx)
            api(libs.coil.compose)
            api(libs.bundles.androidx.compose)
            api(libs.bundles.google.material)
            api(libs.androidx.paging.compose)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.ui_kmp"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }
}