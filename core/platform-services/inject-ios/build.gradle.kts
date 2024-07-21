plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:platform-services:interactor"))
            implementation(project(":core:platform-services:foss"))
            implementation(libs.bundles.koin.common)
        }
    }
}