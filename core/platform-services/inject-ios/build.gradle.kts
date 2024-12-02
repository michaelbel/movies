plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.platformServices.foss)
        }
    }
}