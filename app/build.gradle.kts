plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    /*id ("com.google.devtools.ksp")*/
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ir.hasanazimi.devlab"
    compileSdk = 35

    defaultConfig {
        applicationId = "ir.hasanazimi.jetpackcompose.devlab"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Google MaterialDesign
    implementation(libs.material3)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)

    /*  Dagger Hilt  */
    implementation(libs.androidx.legacy.support.v4)
    androidTestImplementation(libs.androidx.rules)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("org.jsoup:jsoup:1.17.2")


    implementation("io.coil-kt:coil-compose:2.5.0")

/*
    implementation ("androidx.compose.material3:material3:1.2.0")
*/

    implementation ("androidx.navigation:navigation-compose:2.8.9")

    implementation("com.airbnb.android:lottie-compose:6.1.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    // GoogleServices (location)
    implementation(libs.play.services.location)

    // Retrofit by converters
    implementation (libs.retrofit) // okHttp
    implementation (libs.converter.gson) // GSON Converter
    implementation (libs.converter.jackson) // Jackson Converter
    implementation (libs.adapter.rxjava3) // RX3 adapter

    // GSON
    implementation(libs.gson)

    // RxJava/RxAndroid Version 3
    implementation (libs.rxjava)
    implementation (libs.rxkotlin)
    implementation (libs.rxandroid)


    // SharedPreferences (DataStore)
    implementation(libs.androidx.datastore.preferences)

    // Kotlin Reflect
    implementation(libs.kotlin.reflect)


    // chucker
    releaseImplementation (libs.chucker2)
    debugImplementation (libs.chucker1)

    //stetho
    implementation(libs.stetho)
    implementation(libs.stetho.okhttp3)
    implementation(libs.stetho.js.rhino)


    // Biometric (Fingerprint)
    implementation(libs.androidx.biometric)


}