plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:platform-services:interactor"))
            implementation(project(":core:platform-services:foss"))
            implementation(libs.bundles.koin.common)
        }
    }
}