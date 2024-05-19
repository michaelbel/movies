@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://developer.huawei.com/repo/")
    }
}

dependencyResolutionManagement {
    /*repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)*/
    repositories {
        google {
            content {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
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

    ":core:platform-services:gms",
    ":core:platform-services:hms",
    ":core:platform-services:foss",
    ":core:platform-services:inject-android",
    ":core:platform-services:inject-desktop",
    ":core:platform-services:interactor",

    ":core:analytics",
    ":core:common",
    ":core:interactor",
    ":core:navigation",
    ":core:network",
    ":core:notifications",
    ":core:persistence",
    ":core:repository",
    ":core:ui",
    ":core:widget",
    ":core:work",

    ":feature:main-impl",
    ":feature:account",
    ":feature:account-impl",
    ":feature:auth",
    ":feature:auth-impl",
    ":feature:details",
    ":feature:details-impl",
    ":feature:feed",
    ":feature:feed-impl",
    ":feature:gallery",
    ":feature:gallery-impl",
    ":feature:search",
    ":feature:search-impl",
    ":feature:settings",
    ":feature:settings-impl",

    ":feature:debug",
    ":feature:debug-impl"
)