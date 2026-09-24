plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.google.ksp)
}

kotlin {
    jvm()
    iosArm64()
    iosSimulatorArm64()

    android {
        namespace = "org.michaelbel.movies.persistence"
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.shared.network)
                api(libs.bundles.room.paging.common)
                implementation(libs.bundles.datastore.common)
                implementation(libs.bundles.room.common)
                implementation(libs.bundles.sqlite.common)
                implementation(libs.bundles.okio.common)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.bundles.datastore.android)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.bundles.datastore.desktop)
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation(libs.bundles.sqlite.bundled.ios)
            }
            kotlin.srcDir("build/generated/ksp/iosArm64/iosArm64Main/kotlin")
        }
        val iosSimulatorArm64Main by getting {
            dependencies {
                implementation(libs.bundles.sqlite.bundled.ios)
            }
            kotlin.srcDir("build/generated/ksp/iosSimulatorArm64/iosSimulatorArm64Main/kotlin")
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    add("kspAndroid", libs.bundles.room.compiler.common)
    add("kspJvm", libs.bundles.room.compiler.common)
    add("kspIosArm64", libs.bundles.room.compiler.common)
    add("kspIosSimulatorArm64", libs.bundles.room.compiler.common)

}

room3 {
    schemaDirectory("${rootProject.projectDir}/schemas")
}
