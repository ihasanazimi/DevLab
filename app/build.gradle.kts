plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
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

    kapt {
        correctErrorTypes = true
    }

//    /** for pdf generator */
//    packagingOptions {
//        exclude("META-INF/DEPENDENCIES")
//    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


//    // test libs
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Data Binding Runtime
    implementation("androidx.databinding:databinding-runtime:8.3.1")

    // Google MaterialDesign
    implementation("com.google.android.material:material:1.11.0")

    // GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // Architecture lifecycles
    val archComponentVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$archComponentVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$archComponentVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$archComponentVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$archComponentVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:$archComponentVersion")
    implementation("android.arch.lifecycle:viewmodel:1.1.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    /*  Dagger Hilt  */
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    androidTestImplementation("androidx.test:rules:1.5.0")
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")

    // Glide ImageLoader
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    //Event Bus
//    implementation("org.greenrobot:eventbus:3.3.1")

    // room dataBase
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    androidTestImplementation("androidx.room:room-testing:$roomVersion")

    // Nav Component
    val navigationVersion = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // Circle Image View
//    implementation("de.hdodenhof:circleimageview:3.1.0")

    // lottie animations
    implementation("com.airbnb.android:lottie:5.2.0")

    // SharedPreferences (DataStore)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Pagination
    val pagingVersion = "3.2.1"
    implementation("androidx.paging:paging-common-ktx:$pagingVersion")
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    testImplementation("androidx.paging:paging-common-ktx:$pagingVersion")

    // Kotlin Reflect
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")

//    implementation("com.android.support:multidex:1.0.3")

//    // PDF View
//    implementation("com.dmitryborodin:pdfview-android:1.1.0")

    // persian datePicker
//    implementation("com.github.aliab:Persian-Date-Picker-Dialog:1.8.0")

    // screen shot
    implementation("com.github.nisrulz:screenshott:2.0.0")

    // PDF generator
//    implementation("org.apache.pdfbox:pdfbox:2.0.24")

    // Biometric (Fingerprint)
    implementation("androidx.biometric:biometric:1.1.0")

    // android SVG
//    implementation("com.caverock:androidsvg:1.4")

    // GoogleServices (location)
    implementation("com.google.android.gms:play-services-location:21.2.0")


    implementation("it.sephiroth.android.library:number-sliding-picker:1.1.1")

    // GoogleServices (fireBase)
//    implementation("com.google.firebase:firebase-messaging:23.4.1")

    // Retrofit - it Converts Factories
//    implementation 'com.squareup.retrofit2:retrofit:2.9.0' // okHttp
//    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // GSON Converter
//    implementation ("com.squareup.retrofit2:converter-jackson:2.9.0") // Jackson Converter
//    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0") // RX adapter

    /*// Dynamic Animation
    implementation("androidx.dynamicanimation:dynamicanimation:1.0.0")*/

    // RxJava  // RxAndroid
//    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
//    implementation("io.reactivex.rxjava2:rxandroid:2.0.2")
//    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")

    // Koin
//    val koinVersion= "3.5.0"
//    implementation("io.insert-koin:koin-android:$koinVersion")

    // Fresco
//    implementation("com.facebook.fresco:fresco:2.6.0")

    // Picasso
//    implementation("com.squareup.picasso:picasso:2.71828")

    // Coil ImageLoader
//    implementation("io.coil-kt:coil:2.0.0-rc03")


    // OkHttp Log
//    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")



}