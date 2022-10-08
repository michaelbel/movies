pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(
    ":app",

    ":core",
    ":core:ads",
    ":core:analytics",
    ":core:domain",
    ":core:entities",
    ":core:navigation",
    ":core:network",
    ":core:ui",

    ":features",
    ":features:details",
    ":features:feed",
    ":features:settings"
)

rootProject.name = "Movies"