plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // ktorfit
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)

    // json
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "org.koolda.files"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.koolda.files"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "3.6"

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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

    // more icons
    implementation(libs.androidx.material.icons.extended)

    // ktorfit
    implementation(libs.ktorfit.lib)
    implementation(libs.slf4j.simple)

    // json
    implementation(libs.ktor.client.kotlinx)
    implementation(libs.ktor.client.content.negotiation)

    // browser
    implementation(libs.androidx.browser)

    // navigation
    implementation(libs.androidx.navigation.compose)
}