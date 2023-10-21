plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("C:\\Users\\Remilia Scarlet\\.android\\debug.keystore")
        }
    }
    namespace = "vn.edu.hust.formtest"
    compileSdk = 33

    defaultConfig {
        applicationId = "vn.edu.hust.formtest"
        minSdk = 9
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildToolsVersion = "33.0.1"
}

dependencies {

    //implementation("androidx.core:core-ktx:1.9.0")
   // implementation("androidx.appcompat:appcompat:1.6.1")
    //implementation("com.google.android.material:material:1.8.0")
    //implementation("androidx.constraintlayout:constraintlayout:2.1.4")
   // testImplementation("junit:junit:4.13.2")
   // androidTestImplementation("androidx.test.ext:junit:1.1.5")
   // androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.android.support:appcompat-v7:25.4.0")
    implementation("com.android.support.constraint:constraint-layout:1.0.2")
    testImplementation("unit:junit:4.12")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.0")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.0")
}