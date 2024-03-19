@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://developer.huawei.com/repo/")
    }
}

dependencyResolutionManagement {
    /*repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)*/
    repositories {
        mavenCentral()
        google()
        maven(url = "https://developer.huawei.com/repo/")
    }
}

rootProject.name = "movies"

include(
    ":androidApp",
    ":desktopApp",
    ":iosApp",
    ":instant",
    ":benchmark",
    ":shared",

    ":core:analytics",
    ":core:common",
    ":core:interactor-kmp",
    ":core:navigation",
    ":core:network",
    ":core:notifications",
    ":core:persistence",
    ":core:platform-services:gms",
    ":core:platform-services:hms",
    ":core:platform-services:foss",
    ":core:platform-services:inject",
    ":core:platform-services:interactor",
    ":core:repository-kmp",
    ":core:ui",
    ":core:ui-kmp",
    ":core:widget",
    ":core:work",

    ":feature:account-kmp",
    ":feature:account-impl",
    ":feature:auth-kmp",
    ":feature:auth-impl",
    ":feature:details-kmp",
    ":feature:details-impl",
    ":feature:feed-kmp",
    ":feature:feed-impl",
    ":feature:gallery-kmp",
    ":feature:gallery-impl",
    ":feature:search-kmp",
    ":feature:search-impl",
    ":feature:settings-kmp",
    ":feature:settings-impl"
)