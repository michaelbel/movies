import java.nio.charset.StandardCharsets

private val iosVersionName = "3.4.0"
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
            implementation(projects.shared.platformServices.injectIos)
            implementation(projects.feature.main)
        }
    }
}

val generateIosVersionXcconfig = tasks.register("generateIosVersionXcconfig") {
    group = "ios"
    description = "Generates iosApp/Configuration/Version.xcconfig with the current marketing version and build number"
    val versionFile = rootProject.file("iosApp/Configuration/Version.xcconfig")
    outputs.file(versionFile)
    doLast {
        versionFile.writeText(
            """
            MARKETING_VERSION=$iosVersionName
            CURRENT_PROJECT_VERSION=$gitCommitsCount
            """.trimIndent() + "\n"
        )
    }
}

tasks.matching { task ->
    task.name == "embedAndSignAppleFrameworkForXcode"
}.configureEach {
    dependsOn(generateIosVersionXcconfig)
}
