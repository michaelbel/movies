
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.konan.properties.Properties
import org.michaelbel.moviemade.App
import org.michaelbel.moviemade.FirebaseAppDistribution
import org.michaelbel.moviemade.Version
import java.io.FileInputStream
import org.michaelbel.moviemade.KotlinOptions
import org.michaelbel.moviemade.Dependencies

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("com.google.firebase.crashlytics")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = App.CompileSdk
    buildToolsVersion = App.BuildTools

    defaultConfig {
        minSdk = App.MinSdk
        targetSdk = App.TargetSdk
        applicationId = App.ApplicationId
        versionCode = App.VersionCode
        versionName = App.VersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        project.ext.set("archivesBaseName", "moviemade-v$versionName")

        buildConfigField("String", "VERSION_DATE", "\"${System.currentTimeMillis()}\"")
        buildConfigField("String", "TMDB_API_KEY", "\"${gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY")}\"")
        buildConfigField("String", "ADMOB_APP_ID", "\"${gradleLocalProperties(rootDir).getProperty("ADMOB_APP_ID")}\"")
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${gradleLocalProperties(rootDir).getProperty("ADMOB_BANNER_ID")}\"")
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

            firebaseAppDistribution {
                appId = FirebaseAppDistribution.MobileSdkAppId
                artifactType = FirebaseAppDistribution.ArtifactType
                testers = FirebaseAppDistribution.Testers
                releaseNotes = FirebaseAppDistribution.ReleaseNotes
                groups = FirebaseAppDistribution.Groups
                serviceCredentialsFile = "$rootDir/config/firebase-app-distribution.json"
            }
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
        }
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    lint {
        lintConfig = file("lint.xml")
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.KotlinCompilerExtensionVersion
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + KotlinOptions.OptExperimentalMaterial3Api
        freeCompilerArgs = freeCompilerArgs + KotlinOptions.OptExperimentalFoundationApi
        freeCompilerArgs = freeCompilerArgs + KotlinOptions.OptExperimentalSerializationApi
    }
}

dependencies {
    implementation(Dependencies.KotlinCoroutinesCore)
    implementation(Dependencies.KotlinCoroutinesAndroid)
    implementation(Dependencies.KotlinSerialization)
    implementation(Dependencies.ComposeCompiler)
    implementation(Dependencies.ComposeFoundation)
    implementation(Dependencies.ComposeMaterial3)
    implementation(Dependencies.ComposeRuntime)
    implementation(Dependencies.ComposeUi)
    implementation(Dependencies.ComposeUiTooling)
    implementation(Dependencies.HiltNavigationCompose)
    implementation(Dependencies.LifecycleViewModelCompose)
    implementation(Dependencies.NavigationCompose)
    implementation(Dependencies.PagingCompose)
    implementation(Dependencies.AccompanistInsets)
    implementation(Dependencies.AccompanistSwipeRefresh)
    implementation(Dependencies.Material)
    implementation(Dependencies.MaterialComposeThemeAdapter)
    implementation(Dependencies.CoilCompose)
    implementation(Dependencies.CoreSplashScreen)
    implementation(Dependencies.Core)
    implementation(Dependencies.ActivityCompose)
    implementation(Dependencies.GmsAds)
    implementation(Dependencies.PlayCore)
    implementation(Dependencies.Timber)
    implementation(Dependencies.HiltAndroid)
              kapt(Dependencies.HiltCompiler)
    implementation(Dependencies.Retrofit)
    implementation(Dependencies.FirebaseAnalytics)
    implementation(Dependencies.FirebaseConfig)
    implementation(Dependencies.FirebaseCore)
    implementation(Dependencies.FirebaseCrashlytics)
    implementation(Dependencies.RetrofitConverterSerialization)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    //implementation("androidx.activity:activity-ktx:${Version.Activity}")
    //implementation("androidx.appcompat:appcompat:${Version.AppCompat}")
    //implementation("androidx.cardview:cardview:${Version.CardView}")
    //implementation("androidx.constraintlayout:constraintlayout:${Version.ConstraintLayout}")
    //implementation("androidx.fragment:fragment-ktx:${Version.Fragment}")
    //implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Version.Lifecycle}")
    //implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Lifecycle}")
    //implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version.Lifecycle}")
    //implementation("androidx.navigation:navigation-fragment-ktx:${Version.Navigation}")
    //implementation("androidx.navigation:navigation-ui-ktx:${Version.Navigation}")
    //implementation("androidx.paging:paging-runtime-ktx:${Version.Paging}")
    //implementation("androidx.palette:palette-ktx:${Version.Palette}")
    //implementation("androidx.recyclerview:recyclerview:${Version.RecyclerView}")

    //implementation("com.github.kirich1409:viewbindingpropertydelegate:${Version.ViewBindingPropertyDelegate}")
    //implementation("com.jakewharton.timber:timber:${Version.Timber}")
    //implementation("com.squareup.retrofit2:converter-gson:${Version.RetrofitVersion}")
    //implementation("io.coil-kt:coil:${Version.Coil}")

    //debugImplementation("com.squareup.leakcanary:leakcanary-android:${Version.Leakcanary}")
}