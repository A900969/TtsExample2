plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.eviden.ttsexample2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.eviden.ttsexample2"
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // For tts and mediaplayer
    implementation("com.google.android.material:material:1.10.0")
    implementation ("androidx.activity:activity-ktx:1.8.1")
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.compose.foundation:foundation:1.6.0-beta02")
    implementation ("androidx.compose.material:material:1.6.0-beta02")
    implementation ("androidx.compose.ui:ui-tooling:1.6.0-beta02")

    implementation ("androidx.media2:media2-session:1.2.1")
    implementation ("androidx.media2:media2-widget:1.2.1")
    implementation ("androidx.media2:media2-player:1.2.1")
}