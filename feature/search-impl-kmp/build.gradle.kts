plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    id("movies-android-hilt")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = rootProject.extra.get("jvmTarget") as String
            }
        }
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = rootProject.extra.get("jvmTarget") as String
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.constraintlayout.compose.multiplatform)
        }
        androidMain.dependencies {
            api(project(":core:navigation-kmp"))
            api(project(":core:ui"))
            api(project(":core:ui-kmp"))
            implementation(project(":core:common-kmp"))
            implementation(project(":core:interactor-kmp"))
            implementation(project(":core:network-kmp"))
            implementation(project(":core:notifications"))
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(compose.material3)
            implementation(libs.precompose)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.search_impl_kmp"

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