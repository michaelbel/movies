plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.gradle.plugin)
    implementation(libs.kotlin.plugin)
    implementation(libs.javapoet)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "movies-android-application"
            implementationClass = "org.michaelbel.moviemade.plugins.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "movies-android-library"
            implementationClass = "org.michaelbel.moviemade.plugins.AndroidLibraryConventionPlugin"
        }
        register("androidComposeApplication") {
            id = "movies-android-application-compose"
            implementationClass = "org.michaelbel.moviemade.plugins.AndroidComposeApplicationConventionPlugin"
        }
        register("androidComposeLibrary") {
            id = "movies-android-library-compose"
            implementationClass = "org.michaelbel.moviemade.plugins.AndroidComposeLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "movies-android-hilt"
            implementationClass = "org.michaelbel.moviemade.plugins.AndroidHiltConventionPlugin"
        }
    }
}