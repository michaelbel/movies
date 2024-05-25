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
            api(project(":feature:account"))
            api(project(":feature:auth"))
            api(project(":feature:details"))
            api(project(":feature:feed"))
            api(project(":feature:gallery"))
            api(project(":feature:search"))
            api(project(":feature:settings"))
        }
        androidMain.dependencies {
            api(project(":feature:debug"))
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(project(":core:platform-services:inject-desktop"))
            implementation(libs.koin.compose)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.main_impl"
    flavorDimensions += "version"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    productFlavors {
        create("gms") {
            dimension = "version"
            isDefault = true
        }
        create("hms") {
            dimension = "version"
        }
        create("foss") {
            dimension = "version"
        }
    }

    buildFeatures {
        buildConfig = true
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

    val gmsImplementation by configurations
    val hmsImplementation by configurations
    val fossImplementation by configurations
    dependencies {
        gmsImplementation(project(":core:platform-services:inject-android"))
        hmsImplementation(project(":core:platform-services:inject-android"))
        fossImplementation(project(":core:platform-services:inject-android"))
    }
}