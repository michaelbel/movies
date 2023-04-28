plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.gradle.plugin)
    compileOnly(libs.kotlin.plugin)
    implementation(libs.javapoet)
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "movies-android-hilt"
            implementationClass = "plugins.AndroidHiltConventionPlugin"
        }
    }
}