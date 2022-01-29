buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(org.michaelbel.moviemade.Dependencies.Gradle)
        classpath(org.michaelbel.moviemade.Dependencies.KotlinPlugin)
        classpath(org.michaelbel.moviemade.Dependencies.NavigationPlugin)
        classpath(org.michaelbel.moviemade.Dependencies.HiltPlugin)
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