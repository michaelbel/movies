/*pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build.gradle:7.0.4")
            }
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.6.0")
            }
        }
    }
}*/

rootProject.name = "Moviemade"

include(":moviemade")