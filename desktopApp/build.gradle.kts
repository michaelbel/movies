import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.nio.charset.StandardCharsets

private val desktopVersionName = "3.4.0"
private val gitCommitsCount by lazy {
    try {
        val isWindows = System.getProperty("os.name").contains("Windows", ignoreCase = true)
        val processBuilder = when {
            isWindows -> ProcessBuilder("cmd", "/c", "git", "rev-list", "--count", "HEAD")
            else -> ProcessBuilder("git", "rev-list", "--count", "HEAD")
        }
        processBuilder.redirectErrorStream(true)
        processBuilder.start().inputStream.bufferedReader(StandardCharsets.UTF_8).readLine().trim().toInt()
    } catch (_: Exception) {
        1
    }
}

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(projects.shared.platformServices.injectJvm)
            implementation(projects.feature.about)
            implementation(projects.feature.main)
            implementation(libs.slf4j.simple)
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.michaelbel.movies.MainWindowKt"
        javaHome = System.getenv("JAVA_HOME")
            ?: "/Users/mihailbelyj/Library/Java/JavaVirtualMachines/corretto-17.0.15/Contents/Home"
        jvmArgs += listOf(
            "-Dapple.awt.application.name=Movies",
            "-Xdock:name=Movies",
            "-Dmovies.version=$desktopVersionName",
            "-Dmovies.build=$gitCommitsCount",
            "--add-opens=jdk.unsupported/sun.misc=ALL-UNNAMED",
            "--add-opens=java.base/java.lang=ALL-UNNAMED",
            "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
            "--add-opens=java.base/java.io=ALL-UNNAMED",
            "--add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED"
        )

        buildTypes.release.proguard {
            isEnabled.set(false)
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Movies"
            packageVersion = desktopVersionName

            modules("jdk.unsupported", "jdk.unsupported.desktop")

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
    description = "Prints the current desktop packageVersion to stdout."
    doLast {
        println(compose.desktop.application.nativeDistributions.packageVersion)
    }
}
