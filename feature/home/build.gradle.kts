plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    //alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.mck.home"
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
        kotlinCompilerExtensionVersion = "1.5.15"
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


    implementation(project(":core"))
    implementation(project(":common:composable"))
    implementation(project(":common:theme"))

    implementation (libs.androidx.emoji2)

    implementation (libs.coil.compose)
    implementation ("androidx.media3:media3-exoplayer: 1.4.0")
    implementation ("androidx.media3:media3-exoplayer-dash: 1.4.0")
    implementation ("androidx.media3:media3-ui:1.4.0")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation (libs.androidx.constraintlayout.compose)
    //implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


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
    implementation(project(":data"))
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}