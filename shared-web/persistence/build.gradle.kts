@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.google.ksp)
}

kotlin {
    js { browser {} }
    wasmJs { browser {} }

    sourceSets {
        commonMain.dependencies {
            api(projects.shared.network)
            api(libs.bundles.room.common)
            implementation(libs.bundles.sqlite.web)
        }
        jsMain.dependencies {
            implementation(npm("@michaelbel/movies-sqlite-web-worker", file("web-worker")))
        }
        wasmJsMain.dependencies {
            implementation(npm("@michaelbel/movies-sqlite-web-worker", file("web-worker")))
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    add("kspJs", libs.bundles.room.compiler.common)
    add("kspWasmJs", libs.bundles.room.compiler.common)
}

room3 {
    schemaDirectory("$projectDir/schemas")
}
