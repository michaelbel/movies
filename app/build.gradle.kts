@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import org.apache.commons.io.output.ByteArrayOutputStream
import org.jetbrains.kotlin.konan.properties.Properties
import org.michaelbel.moviemade.App
import org.michaelbel.moviemade.App.ApplicationId
import org.michaelbel.moviemade.App.BuildTools
import org.michaelbel.moviemade.App.CompileSdk
import org.michaelbel.moviemade.App.MinSdk
import org.michaelbel.moviemade.App.TargetSdk
import org.michaelbel.moviemade.App.VersionName
import org.michaelbel.moviemade.FirebaseAppDistribution
import org.michaelbel.moviemade.dependencies.KotlinCompilerExtensionVersion
import org.michaelbel.moviemade.dependencies.OptExperimentalCoroutinesApi
import org.michaelbel.moviemade.dependencies.OptExperimentalFoundationApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.OptExperimentalSerializationApi
import org.michaelbel.moviemade.dependencies.implementationActivityDependencies
import org.michaelbel.moviemade.dependencies.implementationChuckerDependencies
import org.michaelbel.moviemade.dependencies.implementationCoilDependencies
import org.michaelbel.moviemade.dependencies.implementationComposeDependencies
import org.michaelbel.moviemade.dependencies.implementationDataStoreDependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies
import org.michaelbel.moviemade.dependencies.implementationKotlinDependencies
import org.michaelbel.moviemade.dependencies.implementationMaterialDependencies
import org.michaelbel.moviemade.dependencies.implementationNavigationDependencies
import org.michaelbel.moviemade.dependencies.implementationTimberDependencies

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
    compileSdk = CompileSdk
    buildToolsVersion = BuildTools

    defaultConfig {
        minSdk = MinSdk
        targetSdk = TargetSdk
        applicationId = ApplicationId
        versionCode = gitVersion
        versionName = VersionName
        testInstrumentationRunner = App.TestInstrumentationRunner
        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "VERSION_DATE", "\"${System.currentTimeMillis()}\"")
        buildConfigField("String", "TMDB_API_KEY", "\"${gradleLocalProperties(rootDir).getProperty("TMDB_API_KEY")}\"")
        buildConfigField("String", "ADMOB_APP_ID", "\"${gradleLocalProperties(rootDir).getProperty("ADMOB_APP_ID")}\"")
        buildConfigField("String", "ADMOB_BANNER_ID", "\"${gradleLocalProperties(rootDir).getProperty("ADMOB_BANNER_ID")}\"")

        setProperty("archivesBaseName", "Movies-v$versionName($versionCode)")
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
        kotlinCompilerExtensionVersion = KotlinCompilerExtensionVersion
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalMaterial3Api
        freeCompilerArgs = freeCompilerArgs + OptExperimentalFoundationApi
        freeCompilerArgs = freeCompilerArgs + OptExperimentalSerializationApi
        freeCompilerArgs = freeCompilerArgs + OptExperimentalCoroutinesApi
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features"))
    implementationDataStoreDependencies()
    implementationCoilDependencies()
    implementationChuckerDependencies()
    implementationActivityDependencies()
    implementationKotlinDependencies()
    implementationHiltDependencies()
    implementationComposeDependencies()
    implementationNavigationDependencies()
    implementationMaterialDependencies()
    implementationTimberDependencies()
}