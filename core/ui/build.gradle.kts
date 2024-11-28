plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project(":core:persistence"))
            api(libs.bundles.coil.common)
            api(libs.bundles.jetbrains.androidx.navigation.compose.common)
            api(libs.bundles.jetbrains.androidx.core.bundle.common)
            api(compose.animation)
            api(compose.foundation)
            api(compose.runtime)
            api(compose.runtimeSaveable)
            api(compose.ui)
            api(compose.material)
            api(compose.material3)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)
            implementation(libs.bundles.lifecycle.common)
        }
        androidMain.dependencies {
            api(libs.bundles.core.splashscreen.android)
            api(libs.bundles.palette.android)
            api(libs.bundles.coil.android)
            api(libs.bundles.compose.android)
            api(libs.bundles.google.material.android)
            implementation(libs.bundles.paging.android)
        }
        jvmMain.dependencies {
            api(compose.desktop.common)
            api(compose.desktop.currentOs)
            api(libs.bundles.compose.desktop)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.ui"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }
}