plugins {
    id("movies-android-library")
    id("movies-android-hilt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "org.michaelbel.movies.domain"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            initWith(getByName("release"))
        }
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.paging.ExperimentalPagingApi",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}

dependencies {
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:entities"))
    implementation(project(":core:network"))
    implementation(libs.bundles.datastore)
    implementation(libs.bundles.room)
    api(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)
    api(libs.androidx.work.runtime.ktx)
    ksp(libs.androidx.room.compiler)
}