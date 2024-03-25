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

    ":core:analytics-kmp",
    ":core:common-kmp",
    ":core:debug-kmp",
    ":core:interactor-kmp",
    ":core:navigation-kmp",
    ":core:network-kmp",
    ":core:notifications-kmp",
    ":core:persistence-kmp",
    ":core:platform-services:gms",
    ":core:platform-services:hms",
    ":core:platform-services:foss",
    ":core:platform-services:inject",
    ":core:platform-services:interactor",
    ":core:repository-kmp",
    ":core:ui-kmp",
    ":core:widget-kmp",
    ":core:work-kmp",

    ":feature:account-kmp",
    ":feature:account-impl-kmp",
    ":feature:auth-kmp",
    ":feature:auth-impl-kmp",
    ":feature:details-kmp",
    ":feature:details-impl-kmp",
    ":feature:feed-kmp",
    ":feature:feed-impl-kmp",
    ":feature:gallery-kmp",
    ":feature:gallery-impl-kmp",
    ":feature:search-kmp",
    ":feature:search-impl-kmp",
    ":feature:settings-kmp",
    ":feature:settings-impl-kmp"
)