plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    buildFeatures {
        viewBinding = true
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("C:\\Users\\Remilia Scarlet\\.android\\debug.keystore")
        }
    }
    namespace = "vn.edu.hust.graduationproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "vn.edu.hust.graduationproject"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildToolsVersion = "35.0.0"

}
dependencies {

    //implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.dagger:hilt-android:2.52")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity-ktx:1.9.3")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.firebase:firebase-storage:21.0.1")
    kapt("com.google.dagger:hilt-android-compiler:2.52")

    //implementation("com.google.android.material:material:1.8.0")
    //implementation("androidx.constraintlayout:constraintlayout:2.1.4")
   //testImplementation("junit:junit:4.13.2")
    //androidTestImplementation("android.arch.core:core-testing:1.1.1")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("com.android.databinding:viewbinding:8.7.2")
    implementation("com.android.support.constraint:constraint-layout:2.0.4")
    implementation("com.android.support:recyclerview-v7:28.0.0")
    implementation("com.google.firebase:firebase-firestore:25.1.1")
    apply(plugin = "kotlin-kapt")
    implementation("com.github.leandroborgesferreira:loading-button-android:2.3.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //implementation("io.github.vejei.viewpagerindicator:viewpagerindicator:1.0.0-alpha.1")
    implementation("com.github.shuhart:stepview:1.5.1")

    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")

    implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.9.0")
}
