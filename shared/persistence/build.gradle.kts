@file:OptIn(ExperimentalWasmDsl::class, ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.google.ksp)
}

kotlin {
    jvm()
    iosArm64()
    iosSimulatorArm64()
    js { browser {} }
    wasmJs { browser {} }

    applyHierarchyTemplate(KotlinHierarchyTemplate.default) {
        common {
            group("web") {
                withJs()
                withWasmJs()
            }
        }
    }

    android {
        namespace = "org.michaelbel.movies.persistence"
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.network)
            api(libs.bundles.room.paging.common)
            implementation(libs.bundles.datastore.common)
            implementation(libs.bundles.room.common)
            implementation(libs.bundles.okio.common)
        }
        androidMain.dependencies {
            implementation(libs.bundles.datastore.android)
            implementation(libs.bundles.sqlite.common)
        }
        jvmMain.dependencies {
            implementation(libs.bundles.datastore.desktop)
            implementation(libs.bundles.sqlite.common)
        }
        getByName("iosArm64Main") {
            dependencies {
                implementation(libs.bundles.sqlite.common)
                implementation(libs.bundles.sqlite.bundled.ios)
            }
            kotlin.srcDir("build/generated/ksp/iosArm64/iosArm64Main/kotlin")
        }
        getByName("iosSimulatorArm64Main") {
            dependencies {
                implementation(libs.bundles.sqlite.common)
                implementation(libs.bundles.sqlite.bundled.ios)
            }
            kotlin.srcDir("build/generated/ksp/iosSimulatorArm64/iosSimulatorArm64Main/kotlin")
        }
        jsMain.dependencies {
            implementation(libs.bundles.sqlite.web)
            implementation(npm("@michaelbel/movies-sqlite-web-worker", file("web-worker")))
        }
        wasmJsMain.dependencies {
            implementation(libs.bundles.sqlite.web)
            implementation(npm("@michaelbel/movies-sqlite-web-worker", file("web-worker")))
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    add("kspAndroid", libs.bundles.room.compiler.common)
    add("kspJvm", libs.bundles.room.compiler.common)
    add("kspIosArm64", libs.bundles.room.compiler.common)
    add("kspIosSimulatorArm64", libs.bundles.room.compiler.common)
    add("kspJs", libs.bundles.room.compiler.common)
    add("kspWasmJs", libs.bundles.room.compiler.common)
}

room3 {
    schemaDirectory("${rootProject.projectDir}/schemas")
}
