plugins {
    //id("com.android.application") version "7.3.1" apply false
    //id("com.android.library") version "7.3.1" apply false
    //id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    //id("org.jetbrains.kotlin.kapt") version "1.7.20" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20" apply false
    id("com.google.gms.google-services") version "4.3.10" apply false
    id("com.google.firebase.crashlytics") version "2.9.2" apply false
    id("com.google.firebase.appdistribution") version "3.0.3" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.5.2" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.diffplug.spotless") version "6.11.0"
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    }
}

/*allprojects {
    spotless {
        kotlinGradle {
            ktlint()
        }
    }
}*/