plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp") version "1.9.22-1.0.16"
}

android {
    namespace = "com.mikhail.dnstestquest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mikhail.dnstestquest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.ktx)
    implementation(libs.lifecycle)
    implementation(libs.viewmodel)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.graphics)
    implementation(libs.compose.tooling)
    implementation(libs.compose.material)
    implementation(libs.compose.navigation)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.retrofit)
    implementation(libs.coroutines)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.android.compiler)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    kapt(libs.hilt.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.ksp)
    debugImplementation(libs.compose.tooling.preview)
    debugImplementation(libs.compose.test.manifest)
}