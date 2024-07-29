plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    id ("com.google.devtools.ksp")
}

android {
    namespace = "ir.ha.meproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "ir.ha.meproject"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

//    kapt {
//        correctErrorTypes = true
//    }

//    /** for pdf generator */
//    packagingOptions {
//        exclude("META-INF/DEPENDENCIES")
//    }

}

dependencies {

    // test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // base
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    // Data Binding Runtime
    implementation(libs.androidx.databinding.runtime)

    // Google MaterialDesign
    implementation(libs.material)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Architecture lifecycles
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)
    implementation(libs.viewmodel)
    implementation(libs.androidx.lifecycle.extensions)

    /*  Dagger Hilt  */
    implementation(libs.androidx.legacy.support.v4)
    androidTestImplementation(libs.androidx.rules)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Glide ImageLoader
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    //Event Bus
    /* implementation("org.greenrobot:eventbus:3.3.1") */

    // room dataBase
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)

    // Nav Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Circle Image View
    //implementation("de.hdodenhof:circleimageview:3.1.0")

    // lottie animations
    implementation(libs.lottie)

    // SharedPreferences (DataStore)
    implementation(libs.androidx.datastore.preferences)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    // Pagination
    implementation(libs.androidx.paging.common.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    testImplementation(libs.androidx.paging.common.ktx)

    // Kotlin Reflect
    implementation(libs.kotlin.reflect)

    // Multidex
    //implementation("com.android.support:multidex:1.0.3")

    // PDF View
    //implementation("com.dmitryborodin:pdfview-android:1.1.0")

    // persian datePicker
    //implementation("com.github.aliab:Persian-Date-Picker-Dialog:1.8.0")

    // screen shot
    //implementation("com.github.nisrulz:screenshott:2.0.0")

    // Biometric (Fingerprint)
    implementation(libs.androidx.biometric)

    // GoogleServices (location)
    implementation(libs.play.services.location)

    // number picker btn style
    //implementation("it.sephiroth.android.library:number-sliding-picker:1.1.1")

    // GoogleServices (fireBase)
    //implementation("com.google.firebase:firebase-messaging:23.4.1")

    // Retrofit by converters
    implementation (libs.retrofit) // okHttp
    implementation (libs.converter.gson) // GSON Converter
    implementation (libs.converter.jackson) // Jackson Converter
    implementation (libs.adapter.rxjava3) // RX3 adapter
    /* implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0") */ // RX2 adapter

    // GSON
    implementation(libs.gson)

    // RxJava/RxAndroid Version 3
    implementation (libs.rxjava)
    implementation (libs.rxkotlin)
    implementation (libs.rxandroid)

    // RxJava/RxAndroid Version 2
    /* implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.2")
    */


    // Koin
    //val koinVersion= "3.5.0"
    //implementation("io.insert-koin:koin-android:$koinVersion")

    // Fresco
    //implementation("com.facebook.fresco:fresco:2.6.0")

    // Picasso
    // implementation("com.squareup.picasso:picasso:2.71828")

    // Coil ImageLoader
    //implementation("io.coil-kt:coil:2.0.0-rc03")


    // OkHttp Log
    //implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // chucker
    releaseImplementation (libs.chucker2)
    debugImplementation (libs.chucker1)

    //stetho
    implementation(libs.stetho)
    implementation(libs.stetho.okhttp3)
    implementation(libs.stetho.js.rhino)




}