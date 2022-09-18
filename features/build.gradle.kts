import org.michaelbel.moviemade.App.CompileSdk

plugins {
    id("com.android.library")
}

android {
    compileSdk = CompileSdk
}

dependencies {
    api(project(":features:feed"))
    api(project(":features:details"))
    api(project(":features:settings"))
}