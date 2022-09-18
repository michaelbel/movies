buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(org.michaelbel.moviemade.dependencies.Gradle)
        classpath(org.michaelbel.moviemade.dependencies.KotlinPlugin)
        classpath(org.michaelbel.moviemade.dependencies.KotlinSerializationPlugin)
        classpath(org.michaelbel.moviemade.dependencies.NavigationSafeArgsPlugin)
        classpath(org.michaelbel.moviemade.dependencies.HiltPlugin)
        classpath(org.michaelbel.moviemade.dependencies.GoogleServicesPlugin)
        classpath(org.michaelbel.moviemade.dependencies.FirebaseCrashlyticsPlugin)
        classpath(org.michaelbel.moviemade.dependencies.FirebaseAppDistributionPlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean").configure {
    delete("build")
}