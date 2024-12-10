plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "com.mck.theme"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


}

dependencies {
    // Splash Screen
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // Core Compose UI
    implementation (libs.ui)

    // Material 3 components for theming and typography
    implementation (libs.material3)

    // Foundation components for layout and other utilities
    implementation (libs.androidx.foundation)

    // Optional: Preview tool for Compose
    implementation (libs.ui.tooling.preview)

    // Kotlin runtime for Compose-related operations
    implementation (libs.androidx.runtime)

    // Navigation support for Compose (if needed for navigation)
    implementation (libs.androidx.navigation.compose)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}