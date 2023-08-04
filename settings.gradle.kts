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

    ":core:analytics",
    ":core:common",
    ":core:domain",
    ":core:entities",
    ":core:navigation",
    ":core:network",
    ":core:notifications",
    ":core:ui",

    ":feature:account",
    ":feature:account-impl",
    ":feature:auth",
    ":feature:auth-impl",
    ":feature:details",
    ":feature:details-impl",
    ":feature:feed",
    ":feature:feed-impl",
    ":feature:settings",
    ":feature:settings-impl"
)