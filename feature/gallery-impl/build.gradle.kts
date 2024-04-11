plugins {
    alias(libs.plugins.kotlin.multiplatform)
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
    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = rootProject.extra.get("jvmTarget") as String
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":core:navigation"))
            api(project(":core:ui"))
            api(project(":core:common"))
            api(project(":core:interactor"))
            api(project(":core:work"))
            implementation(libs.bundles.constraintlayout.common)
            implementation(libs.bundles.koin.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.koin.compose.android)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.gallery_impl"
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