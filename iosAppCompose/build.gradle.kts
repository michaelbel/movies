plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    iosX64("iosX64") {
        binaries.framework {
            baseName = "iosAppCompose"
            isStatic = true
        }
    }
    iosArm64("iosArm64") {
        binaries.framework {
            baseName = "iosAppCompose"
            isStatic = true
        }
    }
    iosSimulatorArm64("iosSimulatorArm64") {
        binaries.framework {
            baseName = "iosAppCompose"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        val iosX64Main by getting
        iosX64Main.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        val iosArm64Main by getting
        iosArm64Main.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        val iosSimulatorArm64Main by getting
        iosSimulatorArm64Main.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            //implementation(project(":feature:feed"))
        }
    }
}