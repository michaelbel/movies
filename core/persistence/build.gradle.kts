@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.google.ksp)
}

kotlin {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    // Room: Adding ksp src directory to use AppDatabase::class.instantiateImpl() in iosMain.
    // Comment when build Android & Desktop apps. Uncomment when first build iOS app.
    /*sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }*/

    sourceSets {
        commonMain.dependencies {
            api(project(":core:network"))
            implementation(libs.bundles.datastore.common)
            implementation(libs.bundles.room.common)
            implementation(libs.bundles.sqlite.common)
            implementation(libs.bundles.okio.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.datastore.android)
        }
        jvmMain.dependencies {
            implementation(libs.bundles.datastore.desktop)
        }
        iosMain.dependencies {
            implementation(libs.bundles.sqlite.bundled.ios)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

android {
    namespace = "org.michaelbel.movies.persistence"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    add("kspAndroid", libs.bundles.room.compiler.common)
    add("kspJvm", libs.bundles.room.compiler.common)
    add("kspIosX64", libs.bundles.room.compiler.common)
    add("kspIosArm64", libs.bundles.room.compiler.common)
    add("kspIosSimulatorArm64", libs.bundles.room.compiler.common)

    /**
     * Need to generate AppDatabase::class.instantiateImpl() in iosMain.
     * Comment when build Android & Desktop apps. Uncomment when first build iOS app.
     */
    /*add("kspCommonMainMetadata", libs.bundles.room.compiler.common)*/
}

room {
    schemaDirectory("${rootProject.projectDir}/schemas")
}