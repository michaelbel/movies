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
        classpath(org.michaelbel.moviemade.Dependencies.GooglePlayServicesPlugin)
        classpath(org.michaelbel.moviemade.Dependencies.FirebaseCrashlyticsPlugin)
        classpath(org.michaelbel.moviemade.Dependencies.FirebaseAppDistributionPlugin)
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