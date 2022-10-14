plugins {
    //id("com.android.application") version "7.3.1" apply false
    //id("com.android.library") version "7.3.1" apply false
    //id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    //id("org.jetbrains.kotlin.kapt") version "1.7.20" apply false
    //id("org.jetbrains.kotlin.plugin.parcelize") version "1.7.20" apply false
    id("com.google.gms.google-services") version "4.3.10" apply false
    id("com.google.firebase.crashlytics") version "2.9.2" apply false
    id("com.google.firebase.appdistribution") version "3.0.3" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.5.2" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.diffplug.spotless") version "6.11.0"
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath(org.michaelbel.moviemade.dependencies.Gradle)
        classpath(org.michaelbel.moviemade.dependencies.KotlinPlugin)
        classpath(org.michaelbel.moviemade.dependencies.KotlinSerializationPlugin)
    }
}

/*allprojects {
    spotless {
        kotlinGradle {
            ktlint()
        }
    }
}*/

/*tasks.register("clean").configure {
    delete("build")
}*/