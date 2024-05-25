plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = rootProject.extra.get("jvmTarget") as String
            }
        }
    }
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            api(project(":core:navigation"))
            api(project(":core:ui"))
            api(project(":core:common"))
            api(project(":core:interactor"))
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(libs.bundles.constraintlayout.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.work.android)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.koin.compose)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.account_impl"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(rootProject.extra.get("jvmTarget") as String)
        targetCompatibility = JavaVersion.toVersion(rootProject.extra.get("jvmTarget") as String)
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}