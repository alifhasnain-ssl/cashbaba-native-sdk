plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.app.cashbabanativesdk" // Keep the same namespace
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        // Library modules don’t use `applicationId`
        // Versioning for the library
//        versionCode = 1
//        versionName = "1.0"

        // Consumers' ProGuard rules
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        // Profile build type for library usage
        create("profile") {
            initWith(getByName("debug")) // Profile inherits from debug
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.alifhasnain-ssl"
                artifactId = "cashbaba-native-sdk"
                version = "0.0.3"
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Debug, release, and profile dependencies for the library
    debugImplementation("com.example.cashbaba_merchant_sdk:flutter_debug:1.0")
    releaseImplementation("com.example.cashbaba_merchant_sdk:flutter_release:1.0")
    add("profileImplementation", "com.example.cashbaba_merchant_sdk:flutter_profile:1.0")

    implementation("androidx.startup:startup-runtime:1.2.0")

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
