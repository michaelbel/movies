plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
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
            implementation(project(":core:common-kmp"))
            implementation(libs.constraintlayout.compose.multiplatform)
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.runtimeSaveable)
            implementation(compose.materialIconsExtended)
            implementation(compose.preview)
            implementation(compose.ui)
            implementation(compose.uiTooling)
            api(libs.coil3)
        }
        androidMain.dependencies {
            implementation(project(":core:common-kmp"))
            implementation(project(":core:network-kmp"))
            implementation(project(":core:persistence-kmp"))
            api(libs.androidx.core.splashscreen)
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
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
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