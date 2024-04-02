import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(project(":core:common-kmp"))
            implementation(project(":core:ui-kmp"))
            implementation(project(":feature:account-kmp"))
            implementation(project(":feature:auth-kmp"))
            implementation(project(":feature:details-kmp"))
            implementation(project(":feature:feed-kmp"))
            implementation(project(":feature:gallery-kmp"))
            implementation(project(":feature:search-kmp"))
            implementation(project(":feature:settings-kmp"))
            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.common)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.components.resources)
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.michaelbel.movies.MoviesDesktopKt"

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