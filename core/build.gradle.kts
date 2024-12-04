plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.mck.core"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // Jetpack Compose
    implementation (libs.ui) // Core Compose UI
    implementation (libs.material3) // Material 3 components
    implementation (libs.androidx.foundation )// Foundation components
    implementation (libs.androidx.foundation.layout) // Layout components
    implementation (libs.androidx.material) // Material components (optional)
    implementation (libs.ui.tooling.preview) // For UI Preview in Android Studio
    implementation (libs.androidx.runtime) // Compose runtime
    implementation (libs.androidx.ui.text )// For text-related components like ClickableText
    implementation (libs.androidx.navigation.compose) // For navigation with Compose

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}