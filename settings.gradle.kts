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
    ":android-app",
    ":desktop-app",
    ":ios-app",
    ":instant",
    ":benchmark",
    ":shared",

    ":core:analytics",
    ":core:common",
    ":core:interactor",
    ":core:navigation",
    ":core:network",
    ":core:notifications",
    ":core:persistence",
    ":core:platform-services:gms",
    ":core:platform-services:hms",
    ":core:platform-services:foss",
    ":core:platform-services:inject",
    ":core:platform-services:interactor",
    ":core:repository",
    ":core:ui",
    ":core:ui-kmp",
    ":core:widget",
    ":core:work",

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
    ":feature:settings-impl"
)