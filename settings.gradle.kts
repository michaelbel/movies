@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

include(
    ":app",

    ":core:ads",
    ":core:analytics",
    ":core:common",
    ":core:domain",
    ":core:entities",
    ":core:navigation",
    ":core:network",
    ":core:ui",

    ":feature:details",
    ":feature:feed",
    ":feature:settings"
)

rootProject.name = "movies"