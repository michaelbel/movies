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
import org.michaelbel.moviemade.dependencies.KotlinCompilerExtensionVersion
import org.michaelbel.moviemade.dependencies.OptExperimentalLifecycleComposeApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies

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

val currentTime: Long by lazy {
    System.currentTimeMillis()
}

val admobAppId: String by lazy {
    gradleLocalProperties(rootDir).getProperty("ADMOB_APP_ID")
}

val admobBannerId: String by lazy {
    gradleLocalProperties(rootDir).getProperty("ADMOB_BANNER_ID")
}

tasks.register("prepareReleaseNotes") {
    doLast {
        exec {
            workingDir(rootDir)
            executable("./scripts/gitlog.sh")
        }
    }
}

afterEvaluate {
    tasks.findByName("assembleDebug")?.finalizedBy("prepareReleaseNotes")
    tasks.findByName("assembleRelease")?.finalizedBy("prepareReleaseNotes")
}

android {
    namespace = "org.michaelbel.moviemade"
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

        buildConfigField("String", "VERSION_DATE", "\"$currentTime\"")
        buildConfigField("String", "ADMOB_APP_ID", "\"$admobAppId\"")
        buildConfigField("String", "ADMOB_BANNER_ID", "\"$admobBannerId\"")

        setProperty("archivesBaseName", "Movies-v$versionName($versionCode)")
    }

    signingConfigs {
        val keystoreProperties = Properties()
        val keystorePropertiesFile: File = rootProject.file("config/keystore.properties")
        if (keystorePropertiesFile.exists()) {
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
        } else {
            keystoreProperties["keyAlias"] = System.getenv("KEYSTORE_KEY_ALIAS").orEmpty()
            keystoreProperties["keyPassword"] = System.getenv("KEYSTORE_KEY_PASSWORD").orEmpty()
            keystoreProperties["storePassword"] = System.getenv("KEYSTORE_STORE_PASSWORD").orEmpty()
            keystoreProperties["storeFile"] = System.getenv("KEYSTORE_FILE").orEmpty()
        }

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
                appId = "1:770317857182:android:876190afbc53df31"
                artifactType = "APK"
                testers = "michaelbel24865@gmail.com"
                groups = "qa"
                //releaseNotesFile="$rootProject.rootDir/releaseNotes.txt"
                //serviceCredentialsFile = "$rootDir/config/firebase-app-distribution.json"
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

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = KotlinCompilerExtensionVersion
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalMaterial3Api
        freeCompilerArgs = freeCompilerArgs + OptExperimentalLifecycleComposeApi
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":feature:details"))
    implementation(project(":feature:feed"))
    implementation(project(":feature:settings"))
    implementationHiltDependencies()
}