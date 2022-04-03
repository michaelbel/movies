
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import org.apache.commons.io.output.ByteArrayOutputStream
import org.jetbrains.kotlin.konan.properties.Properties
import org.michaelbel.moviemade.App
import org.michaelbel.moviemade.Dependencies
import org.michaelbel.moviemade.FirebaseAppDistribution
import org.michaelbel.moviemade.KotlinOptions

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

val gitVersion: Int by lazy {
    val stdout = ByteArrayOutputStream()
    rootProject.exec {
        commandLine("git", "rev-list", "--count", "HEAD")
        standardOutput = stdout
    }
    stdout.toString().trim().toInt()
}

android {
    compileSdk = App.CompileSdk
    buildToolsVersion = App.BuildTools

    defaultConfig {
        minSdk = App.MinSdk
        targetSdk = App.TargetSdk
        applicationId = App.ApplicationId
        versionCode = gitVersion
        versionName = App.VersionName
        testInstrumentationRunner = App.TestInstrumentationRunner
        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "VERSION_DATE", "\"${System.currentTimeMillis()}\"")
        buildConfigField("String", "TMDB_API_KEY", "\"${gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY")}\"")
        buildConfigField("String", "ADMOB_APP_ID", "\"${gradleLocalProperties(rootDir).getProperty("ADMOB_APP_ID")}\"")
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${gradleLocalProperties(rootDir).getProperty("ADMOB_BANNER_ID")}\"")

        setProperty("archivesBaseName", "moviemade-v$versionName($versionCode)")
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

    /*lint {
        lintConfig = file("lint.xml")
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }*/

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.KotlinCompilerExtensionVersion
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + KotlinOptions.OptExperimentalMaterial3Api
        freeCompilerArgs = freeCompilerArgs + KotlinOptions.OptExperimentalFoundationApi
        freeCompilerArgs = freeCompilerArgs + KotlinOptions.OptExperimentalSerializationApi
        freeCompilerArgs = freeCompilerArgs + KotlinOptions.OptExperimentalCoroutinesApi
    }
}

dependencies {
    implementation(Dependencies.KotlinCoroutinesCore)
    implementation(Dependencies.KotlinCoroutinesAndroid)
    implementation(Dependencies.KotlinSerializationJson)
    implementation(Dependencies.ActivityCompose)
    implementation(Dependencies.ComposeCompiler)
    implementation(Dependencies.ComposeFoundation)
    implementation(Dependencies.ComposeFoundationLayout)
    implementation(Dependencies.ComposeMaterial3)
    implementation(Dependencies.ComposeRuntime)
    implementation(Dependencies.ComposeUi)
    implementation(Dependencies.ComposeUiTooling)
    implementation(Dependencies.Core)
    implementation(Dependencies.CoreSplashScreen)
    implementation(Dependencies.DataStoreCore)
    implementation(Dependencies.DataStorePreferences)
    implementation(Dependencies.DataStorePreferencesCore)
    implementation(Dependencies.HiltNavigationCompose)
    implementation(Dependencies.LifecycleViewModelCompose)
    implementation(Dependencies.NavigationCompose)
    implementation(Dependencies.PagingCompose)
    implementation(Dependencies.Room)
              kapt(Dependencies.RoomCompiler)
    implementation(Dependencies.Startup)
    implementation(Dependencies.AccompanistInsets)
    implementation(Dependencies.AccompanistInsetsUi)
    implementation(Dependencies.AccompanistSwipeRefresh)
    implementation(Dependencies.HiltAndroid)
              kapt(Dependencies.HiltCompiler)
    implementation(Dependencies.Material)
    implementation(Dependencies.MaterialComposeThemeAdapter)
    implementation(Dependencies.PlayCore)
    implementation(Dependencies.GmsAds)
    implementation(Dependencies.FirebaseAnalytics)
    implementation(Dependencies.FirebaseConfig)
    implementation(Dependencies.FirebaseCore)
    implementation(Dependencies.FirebaseCrashlytics)
    implementation(Dependencies.CoilCompose)
    implementation(Dependencies.Timber)
    implementation(Dependencies.Retrofit)
    implementation(Dependencies.RetrofitConverterSerialization)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
}