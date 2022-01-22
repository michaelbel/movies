import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties
import org.michaelbel.moviemade.App
import org.michaelbel.moviemade.Version
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

apply("../config.gradle")

android {
    compileSdk = App.CompileSdk
    buildToolsVersion = App.BuildTools

    defaultConfig {
        minSdk = App.MinSdk
        targetSdk = App.TargetSdk
        applicationId = App.ApplicationId
        versionCode = App.VersionCode
        versionName = App.VersionName
        project.ext.set("archivesBaseName", "moviemade-v$versionName")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "VERSION_DATE", "\"${System.currentTimeMillis()}\"")
        buildConfigField("String", "TMDB_API_KEY", "\"${gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY")}\"")
    }

    signingConfigs {
        val keystorePropertiesFile = rootProject.file("config/keystore.properties")
        val keystoreProperties = Properties()
        keystoreProperties.load(FileInputStream(keystorePropertiesFile))

        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
        }
    }

    buildFeatures {
        viewBinding = true
    }

    lint {
        lintConfig = file("lint.xml")
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.Coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.Coroutines}")

    implementation("androidx.activity:activity-ktx:${Version.Activity}")
    implementation("androidx.activity:activity-compose:${Version.Activity}")
    implementation("androidx.appcompat:appcompat:${Version.AppCompat}")
    implementation("androidx.cardview:cardview:${Version.CardView}")
    implementation("androidx.constraintlayout:constraintlayout:${Version.ConstraintLayout}")
    implementation("androidx.core:core-ktx:${Version.Core}")
    implementation("androidx.core:core-splashscreen:${Version.CoreSplashScreen}")
    implementation("androidx.fragment:fragment-ktx:${Version.Fragment}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Version.Lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version.Lifecycle}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Version.Navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Version.Navigation}")
    implementation("androidx.paging:paging-runtime-ktx:${Version.Paging}")
    implementation("androidx.palette:palette-ktx:${Version.Palette}")
    implementation("androidx.recyclerview:recyclerview:${Version.RecyclerView}")

    implementation("com.google.android.gms:play-services-ads:${Version.Ads}")
    implementation("com.google.android.play:core-ktx:${Version.PlayCore}")
    implementation("com.google.android.material:material:${Version.Material}")
    implementation("com.google.android.material:compose-theme-adapter:${Version.Compose}")
    implementation("com.google.dagger:hilt-android:${Version.Dagger}")
              kapt("com.google.dagger:hilt-compiler:${Version.Dagger}")
    implementation("com.google.firebase:firebase-analytics-ktx:${Version.FirebaseAnalytics}")
    implementation("com.google.firebase:firebase-core:${Version.FirebaseCore}")
    implementation("com.google.firebase:firebase-crashlytics-ktx:${Version.FirebaseCrashlytics}")
    implementation("com.google.firebase:firebase-config-ktx:${Version.FirebaseConfig}")

    implementation("com.github.kirich1409:viewbindingpropertydelegate:${Version.ViewBindingPropertyDelegate}")
    implementation("com.jakewharton.timber:timber:${Version.Timber}")
    implementation("com.squareup.retrofit2:retrofit:${Version.Retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${Version.Retrofit}")
    implementation("io.coil-kt:coil:${Version.Coil}")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:${Version.Leakcanary}")

    testImplementation("junit:junit:${Version.Junit}")
    testImplementation("org.mockito:mockito-core:${Version.Mockito}")
    testImplementation("io.mockk:mockk:${Version.Mockk}")
    testImplementation("org.robolectric:robolectric:${Version.Robolectric}")
}