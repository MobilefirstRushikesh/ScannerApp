
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}
android {
    namespace = "com.mobilefirst.scannerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mobilefirst.scannerapp"
        minSdk = 27
        targetSdk = 33
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.activity:activity-ktx:1.8.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")



    //scanner lib
    implementation ("com.journeyapps:zxing-android-embedded:4.1.0")
    implementation ("com.google.zxing:core:3.4.1")

    //image cropping lib
    implementation ("com.github.yalantis:ucrop:2.2.6")

    // permission lib
    implementation( "com.guolindev.permissionx:permissionx:1.7.1")

    // Room dependencies

    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")



    implementation ("com.google.dagger:hilt-android:2.49")
    kapt ("com.google.dagger:hilt-compiler:2.49")

// Coroutines for asynchronous programming
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // circle imageview
    implementation ("de.hdodenhof:circleimageview:3.1.0")



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
kapt {
    correctErrorTypes = true
}