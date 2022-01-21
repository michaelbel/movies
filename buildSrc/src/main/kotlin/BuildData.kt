package org.michaelbel.moviemade

object App {
    const val VersionCode = 69
    const val VersionName = "1.3.7"

    const val MinSdk = 21
    const val TargetSdk = 31
    const val CompileSdk = 31
    const val BuildTools = "31.0.0"

    const val ApplicationId = "org.michaelbel.moviemade"

    val generateVersionCode: Int
        get() {
            var result: String = Runtime.getRuntime().exec("git rev-list HEAD --count").toString().trim() // Unix
            print("result 1 = $result")
            if (result.isEmpty()) result = Runtime.getRuntime().exec("PowerShell -Command git rev-list HEAD --count").toString().trim() // Windows
            print("result 2 = $result")
            if (result.isEmpty()) throw RuntimeException("Could not generate versioncode on this platform? Cmd output: $result")
            return result.toInt()
        }
}

object Plugin {
    const val Gradle = "7.0.4"
    const val Kotlin = "1.6.0"
    const val Navigation = "2.3.5"
    const val Dagger = "2.40.5"
    const val Google = "4.3.10"
    const val Crashlytics = "2.8.1"
    const val Appdistribution = "2.2.0"
}

object Version {
    const val Coroutines = "1.6.0"
    const val Activity = "1.4.0"
    const val AppCompat = "1.4.1"
    const val CardView = "1.0.0"
    const val ConstraintLayout = "2.1.3"
    const val Core = "1.7.0"
    const val CoreSplashScreen = "1.0.0-beta01"
    const val Fragment = "1.4.0"
    const val Lifecycle = "2.4.0"
    const val Navigation = "2.3.5"
    const val Paging = "3.1.0"
    const val Palette = "1.0.0"
    const val RecyclerView = "1.2.1"
    const val Ads = "20.5.0"
    const val PlayCore = "1.8.1"
    const val Material = "1.6.0-alpha02"
    const val Compose = "1.0.5"
    const val Dagger = "2.40.5"
    const val FirebaseAnalytics = "20.0.2"
    const val FirebaseCore = "20.0.2"
    const val FirebaseCrashlytics = "18.2.6"
    const val FirebaseConfig = "21.0.1"
    const val ViewBindingPropertyDelegate = "1.5.6"
    const val Timber = "5.0.1"
    const val Retrofit = "2.9.0"
    const val Coil = "1.4.0"
    const val Leakcanary = "2.7"
    const val Junit = "4.13.2"
    const val Mockito = "4.2.0"
    const val Mockk = "1.12.2"
    const val Robolectric = "4.7.3"
}