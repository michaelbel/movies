plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "movies.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
    }
}