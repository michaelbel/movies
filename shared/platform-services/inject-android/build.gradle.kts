plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "org.michaelbel.movies.platform.inject_android"
    compileSdk = libs.versions.compile.sdk.get().toInt()
    flavorDimensions += "version"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
    }

    productFlavors {
        create("gms") {
            dimension = "version"
            isDefault = true
        }
        create("hms") {
            dimension = "version"
        }
        create("foss") {
            dimension = "version"
        }
    }

}

val gmsImplementation = configurations.getByName("gmsImplementation")
val hmsImplementation = configurations.getByName("hmsImplementation")
val fossImplementation = configurations.getByName("fossImplementation")
dependencies {
    implementation(projects.shared.platformServices.interactor)
    gmsImplementation(projects.shared.platformServices.gms)
    hmsImplementation(projects.shared.platformServices.hms)
    fossImplementation(projects.shared.platformServices.foss)
}
