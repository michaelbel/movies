@Suppress("dsl_scope_violation")
plugins {
    alias(libs.plugins.library)
    alias(libs.plugins.kotlin)
    id("movies-android-hilt")
}

android {
    namespace = "org.michaelbel.movies.domain"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    /*buildTypes {
        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            initWith(getByName("release"))
        }
    }*/

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.paging.ExperimentalPagingApi"
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
    api(project(":core:interactor"))
    api(project(":core:persistence"))
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:entities"))
    implementation(project(":core:network"))
    implementation(project(":core:repository-impl"))
    api(libs.androidx.hilt.work)
    api(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.paging.compose)
}