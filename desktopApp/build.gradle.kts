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
            implementation(projects.core.platformServices.injectJvm)
            implementation(projects.feature.mainImpl)
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.michaelbel.movies.MainWindowKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Movies"
            packageVersion = "1.0.0"

            val iconsRoot = project.file("desktop-icons")
            macOS {
                bundleID = "org.michaelbel.movies"
                dockName = "Movies"
                iconFile.set(project.file("desktop-icons").resolve("movies_macos.icns"))
            }
            windows {
                iconFile.set(iconsRoot.resolve("movies-windows.ico"))
                menuGroup = "Movies Menu"
                upgradeUuid = "3e111aef-dba0-434e-82ca-a89155e2d306"
            }
            linux {
                iconFile.set(iconsRoot.resolve("movies-linux.png"))
            }
        }
    }
}

tasks.register("printVersionName") {
    doLast {
        println(compose.desktop.application.nativeDistributions.packageVersion)
    }
}