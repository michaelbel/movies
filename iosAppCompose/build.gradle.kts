plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    iosX64 {
        binaries.framework {
            baseName = "iosAppCompose"
            isStatic = true
        }
    }
    iosArm64 {
        binaries.framework {
            baseName = "iosAppCompose"
            isStatic = true
        }
    }
    iosSimulatorArm64 {
        binaries.framework {
            baseName = "iosAppCompose"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:platform-services:inject-ios"))
            implementation(project(":feature:main-impl"))
        }
        iosMain.dependencies {}
    }
}