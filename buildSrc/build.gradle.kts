plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    implementation("com.squareup:javapoet:1.13.0")
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