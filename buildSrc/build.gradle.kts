plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.gradle.plugin)
    compileOnly(libs.kotlin.plugin)
    implementation(libs.javapoet)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "movies-android-application"
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "movies-android-library"
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }
        register("androidComposeApplication") {
            id = "movies-android-application-compose"
            implementationClass = "plugins.AndroidComposeApplicationConventionPlugin"
        }
        register("androidComposeLibrary") {
            id = "movies-android-library-compose"
            implementationClass = "plugins.AndroidComposeLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "movies-android-hilt"
            implementationClass = "plugins.AndroidHiltConventionPlugin"
        }
    }
}