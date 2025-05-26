plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "deakin.example.studentlearningapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "deakin.example.studentlearningapp"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("androidx.navigation:navigation-fragment:2.5.3")
    implementation ("androidx.navigation:navigation-ui:2.5.3")
    implementation ("com.google.android.gms:play-services-wallet:19.2.0")
    implementation ("org.json:json:20231013")
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    annotationProcessor ("androidx.room:room-compiler:2.6.1")
    implementation ("com.android.volley:volley:1.2.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}