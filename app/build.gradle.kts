//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//}
//
//android {
//    namespace = "com.example.testaar"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.example.testaar"
//        minSdk = 21
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary = true
//        }
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//}
//
//dependencies {
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {

    namespace = "com.example.testaar"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.testaar"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            // Custom debug configurations (if needed)
        }

        getByName("release") {
//            isMinifyEnabled = true
            isMinifyEnabled = false
            isShrinkResources = false
//            minifyEnabled false // Disable ProGuard temporarily
//            shrinkResources false // Disable shrinking resources
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        // Add the profile build type explicitly
        create("profile") {
            initWith(getByName("debug")) // Profile build type inherits from debug
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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

    // Add Flutter AAR dependencie

    //implementation("com.example.cashbaba_merchant_sdk:sdk")

    // implementation(files(File("/home/rafid/Desktop/flutter_sdk_projects/FlutterAARIntegration/app/libs/repo/com/example/cashbaba_merchant_sdk/sdk")))

    debugImplementation("com.example.cashbaba_merchant_sdk:flutter_debug:1.0")
    releaseImplementation("com.example.cashbaba_merchant_sdk:flutter_release:1.0")
    add("profileImplementation", "com.example.cashbaba_merchant_sdk:flutter_profile:1.0")


    //debugImplementation("android_generated.native_image_cropper_android_debug:1.0")
    //releaseImplementation("android_generated.native_image_cropper_android_release:1.0")
    // add("profileImplementation2", "android_generated.native_image_cropper_android_profile:1.0")
}


