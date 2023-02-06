pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "movies"

include(
    ":app",

    ":benchmark",

    ":core:ads",
    ":core:analytics",
    ":core:common",
    ":core:domain",
    ":core:entities",
    ":core:navigation",
    ":core:network",
    ":core:ui",

    ":feature:details-impl",
    ":feature:feed-impl",
    ":feature:settings-impl"
)