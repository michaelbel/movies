
import com.google.firebase.appdistribution.gradle.AppDistributionExtension
import java.io.FileInputStream
import org.apache.commons.io.output.ByteArrayOutputStream
import org.jetbrains.kotlin.konan.properties.Properties

@Suppress("dsl_scope_violation")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.palantir.git)
}

val gitCommitsCount by lazy {
    val stdout = ByteArrayOutputStream()
    rootProject.exec {
        commandLine("git", "rev-list", "--count", "HEAD")
        standardOutput = stdout
    }
    stdout.toString().trim().toInt()
}

val currentTime by lazy {
    System.currentTimeMillis()
}

val gitVersion: groovy.lang.Closure<String> by extra
val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
val versionLastTag: String = versionDetails().lastTag

tasks.register("prepareReleaseNotes") {
    doLast {
        exec {
            workingDir(rootDir)
            executable("./config/scripts/gitlog.sh")
        }
    }
}

afterEvaluate {
    tasks.findByName("assembleGmsDebug")?.finalizedBy("prepareReleaseNotes")
    tasks.findByName("assembleGmsRelease")?.finalizedBy("prepareReleaseNotes")
    tasks.findByName("assembleHmsDebug")?.finalizedBy("prepareReleaseNotes")
    tasks.findByName("assembleHmsRelease")?.finalizedBy("prepareReleaseNotes")
    tasks.findByName("assembleFossDebug")?.finalizedBy("prepareReleaseNotes")
    tasks.findByName("assembleFossRelease")?.finalizedBy("prepareReleaseNotes")
}

android {
    namespace = "org.michaelbel.movies.app"
    flavorDimensions += "version"

    defaultConfig {
        applicationId = "org.michaelbel.moviemade"
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
        versionCode = gitCommitsCount
        versionName = "2.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        resourceConfigurations.addAll(listOf("en", "ru"))

        buildConfigField("String", "VERSION_DATE", "\"$currentTime\"")

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
        create("release") { // todo Uncomment to create a signed release
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
            signingConfig = signingConfigs.getByName("release") // todo Uncomment to create a signed release
            applicationIdSuffix = MoviesBuildType.RELEASE.applicationIdSuffix
            manifestPlaceholders += mapOf("appName" to "@string/app_name")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro",
                "okhttp3.pro",
                "coroutines.pro"
            )
        }
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = MoviesBuildType.DEBUG.applicationIdSuffix
            manifestPlaceholders += mapOf("appName" to "@string/app_name_dev")
            isDefault = true
            //vcsInfo { include = true } // Version control system integration in App Quality Insights
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
        compose = true
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

    /*dynamicFeatures += setOf(":instant")*/

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jdk.get().toInt())
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}

val gmsImplementation by configurations
val hmsImplementation by configurations
val fossImplementation by configurations
dependencies {
    gmsImplementation(project(":core:platform-services:inject-kmp"))
    hmsImplementation(project(":core:platform-services:inject-kmp"))
    fossImplementation(project(":core:platform-services:inject-kmp"))

    implementation(project(":core:common-kmp"))
    implementation(project(":core:debug-kmp"))
    implementation(project(":core:interactor-kmp"))
    implementation(project(":core:interactor-kmp"))
    implementation(project(":core:navigation-kmp"))
    implementation(project(":core:platform-services:interactor-kmp"))
    implementation(project(":core:ui-kmp"))
    implementation(project(":core:widget-kmp"))
    implementation(project(":core:work-kmp"))

    implementation(project(":feature:account-kmp"))
    implementation(project(":feature:auth-kmp"))
    implementation(project(":feature:details-kmp"))
    implementation(project(":feature:feed-kmp"))
    implementation(project(":feature:gallery-kmp"))
    implementation(project(":feature:search-kmp"))
    implementation(project(":feature:settings-kmp"))
    implementation(libs.bundles.kotlin.reflect.android)
    implementation(libs.bundles.koin.compose.work.android)
    testImplementation(libs.bundles.junit.android)
    androidTestImplementation(libs.bundles.test.espresso.android)
    androidTestImplementation(libs.bundles.test.ext.junit.android)
    androidTestImplementation(libs.bundles.compose.ui.test.android)
    androidTestImplementation(libs.bundles.benchmark.android)
    debugImplementation(libs.bundles.leakcanary.android)
    lintChecks(libs.lint.checks)
}

val hasGmsDebug = gradle.startParameter.taskNames.any { it.contains("GmsDebug", ignoreCase = true) }
val hasGmsRelease = gradle.startParameter.taskNames.any { it.contains("GmsRelease", ignoreCase = true) }
val hasGmsBenchmark = gradle.startParameter.taskNames.any { it.contains("GmsBenchmark", ignoreCase = true) }

if (hasGmsDebug || hasGmsRelease || hasGmsBenchmark) {
    apply(plugin = libs.plugins.google.services.get().pluginId)
    apply(plugin = libs.plugins.google.firebase.crashlytics.get().pluginId)
    apply(plugin = libs.plugins.google.firebase.appdistribution.get().pluginId)
}

if (hasGmsRelease) {
    configure<AppDistributionExtension> {
        appId = "1:770317857182:android:876190afbc53df31"
        artifactType = "APK"
        testers = "michaelbel24865@gmail.com"
        groups = "qa"
    }
}

val hasHmsDebug = gradle.startParameter.taskNames.any { it.contains("HmsDebug", ignoreCase = true) }
val hasHmsRelease = gradle.startParameter.taskNames.any { it.contains("HmsRelease", ignoreCase = true) }
val hasHmsBenchmark = gradle.startParameter.taskNames.any { it.contains("HmsBenchmark", ignoreCase = true) }

if (hasHmsDebug || hasHmsRelease || hasHmsBenchmark) {
    //apply(plugin = libs.plugins.huawei.services.get().pluginId)
}