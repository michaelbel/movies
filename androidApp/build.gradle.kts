import com.google.firebase.appdistribution.gradle.AppDistributionExtension
import ktx.isGmsBuild
import ktx.isGmsReleaseBuild
import ktx.isHmsBuild
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

@Suppress("dsl_scope_violation")

plugins {
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.palantir.git)
}
if (isGmsBuild) {
    pluginManager.apply(libs.plugins.google.services.get().pluginId)
    pluginManager.apply(libs.plugins.google.firebase.crashlytics.get().pluginId)
    pluginManager.apply(libs.plugins.google.firebase.appdistribution.get().pluginId)
}
if (isHmsBuild) {
    pluginManager.apply(libs.plugins.huawei.services.get().pluginId)
}

private val gitCommitsCount by lazy {
    ProcessBuilder("git", "rev-list", "--count", "HEAD")
        .redirectErrorStream(true)
        .start().inputStream.bufferedReader().readLine().trim().toInt()
}
private val currentTime by lazy {
    System.currentTimeMillis()
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

android {
    namespace = "org.michaelbel.movies.app"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    flavorDimensions += "version"

    defaultConfig {
        applicationId = "org.michaelbel.moviemade"
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionName = "3.4.0"
        versionCode = gitCommitsCount
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        buildConfigField("String", "VERSION_DATE", "\"$currentTime\"")
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "movies"
            keyPassword = "password"
            storeFile = rootProject.file(".github/debug-key.jks")
            storePassword = "password"
        }

        val keystoreProperties = Properties()
        val keystorePropertiesFile = rootProject.file(".github/keystore.properties")
        if (keystorePropertiesFile.exists()) {
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
        } else {
            keystoreProperties["keyAlias"] = System.getenv("KEYSTORE_KEY_ALIAS").orEmpty()
            keystoreProperties["keyPassword"] = System.getenv("KEYSTORE_KEY_PASSWORD").orEmpty()
            keystoreProperties["storePassword"] = System.getenv("KEYSTORE_STORE_PASSWORD").orEmpty()
            keystoreProperties["storeFile"] = System.getenv("KEYSTORE_FILE").orEmpty()
        }
        val keystoreFile = keystoreProperties["storeFile"] as String
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = if (keystoreFile.isNotEmpty()) file(keystoreFile) else null
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        release {
            manifestPlaceholders += mapOf("appName" to "@string/app_name")
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            applicationIdSuffix = MoviesBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            manifestPlaceholders += mapOf("appName" to "@string/app_name_dev")
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = MoviesBuildType.DEBUG.applicationIdSuffix
        }
        create("benchmark") {
            initWith(getByName("release"))
            matchingFallbacks.add("release")
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles("benchmark-rules.pro")
            isDebuggable = false
            isMinifyEnabled = true
            applicationIdSuffix = MoviesBuildType.BENCHMARK.applicationIdSuffix
        }
    }

    buildFeatures {
        buildConfig = true
    }

    productFlavors {
        create("gms") {
            dimension = "version"
            applicationId = "org.michaelbel.moviemade"
            isDefault = true
        }
        create("hms") {
            dimension = "version"
            applicationId = "org.michaelbel.movies"
        }
        create("foss") {
            dimension = "version"
            applicationId = "org.michaelbel.movies"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

base {
    archivesName.set("Movies-v${android.defaultConfig.versionName}(${android.defaultConfig.versionCode})")
}

val gmsImplementation by configurations
val hmsImplementation by configurations
val fossImplementation by configurations
dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    gmsImplementation(projects.shared.platformServices.injectAndroid)
    hmsImplementation(projects.shared.platformServices.injectAndroid)
    fossImplementation(projects.shared.platformServices.injectAndroid)
    implementation(projects.feature.main)
    implementation(libs.bundles.kotlin.reflect.android)
    testImplementation(projects.shared.network)
    testImplementation(libs.bundles.junit.android)
    androidTestImplementation(libs.bundles.test.espresso.android)
    androidTestImplementation(libs.bundles.test.ext.junit.android)
    androidTestImplementation(libs.bundles.benchmark.android)
    debugImplementation(libs.bundles.leakcanary.android)
}

if (isGmsReleaseBuild) {
    configure<AppDistributionExtension> {
        appId = "1:770317857182:android:876190afbc53df31"
        artifactType = "APK"
        testers = "michaelbel24865@gmail.com"
        groups = "qa"
    }
}

tasks.register("printVersions") {
    description = "Prints the current versionName and versionCode to stdout."
    doLast {
        println("VERSION_NAME=${android.defaultConfig.versionName}")
        println("VERSION_CODE=${android.defaultConfig.versionCode}")
    }
}
