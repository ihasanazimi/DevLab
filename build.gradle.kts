// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.ksp) apply false
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
//        classpath("com.google.gms:google-services:4.4.0")
    }

}