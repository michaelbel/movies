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

    ":core:platform-services:gms-kmp",
    ":core:platform-services:hms-kmp",
    ":core:platform-services:foss-kmp",
    ":core:platform-services:inject-kmp",
    ":core:platform-services:interactor-kmp",

    ":core:analytics-kmp",
    ":core:common-kmp",
    ":core:interactor-kmp",
    ":core:navigation-kmp",
    ":core:network-kmp",
    ":core:notifications-kmp",
    ":core:persistence-kmp",
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
    ":feature:settings-impl-kmp",

    ":feature:debug-kmp",
    ":feature:debug-impl-kmp"
)