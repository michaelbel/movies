import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(project(":core:platform-services:inject-desktop"))
            implementation(project(":feature:main-impl"))
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.michaelbel.movies.MainWindowKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.michaelbel.movies"
            packageVersion = "1.0.0"

            val iconsRoot = project.file("desktop-icons")
            macOS {
                iconFile.set(iconsRoot.resolve("movies-macos.icns"))
            }
            windows {
                iconFile.set(iconsRoot.resolve("movies-windows.ico"))
                menuGroup = "Movies Menu"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "3e111aef-dba0-434e-82ca-a89155e2d306"
            }
            linux {
                iconFile.set(iconsRoot.resolve("movies-linux.png"))
            }
        }
    }
}