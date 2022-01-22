buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
        classpath("com.google.firebase:firebase-appdistribution-gradle:3.0.0")

        /*classpath("com.android.tools.build:gradle:${org.michaelbel.moviemade.Plugin.Gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${org.michaelbel.moviemade.Plugin.Kotlin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${org.michaelbel.moviemade.Plugin.Navigation}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${org.michaelbel.moviemade.Plugin.Dagger}")
        classpath("com.google.gms:google-services:${org.michaelbel.moviemade.Plugin.Google}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${org.michaelbel.moviemade.Plugin.Crashlytics}")
        classpath("com.google.firebase:firebase-appdistribution-gradle:${org.michaelbel.moviemade.Plugin.Appdistribution}")*/
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