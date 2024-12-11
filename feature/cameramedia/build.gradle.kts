plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.mck.cameramedia"
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

    implementation(project(":core"))
    implementation(project(":data"))

    implementation(project(":common:composable"))
    implementation(project(":common:theme"))
    implementation(project(":domain"))

    implementation(libs.guava)

    implementation(libs.hilt.android)

    implementation(libs.androidx.paging.compose)

//    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.snapper)

    // camera
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.coil.compose)

    // accompanist navigation
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.navigation.material)

    // Jetpack Compose
    implementation (libs.ui) // Core Compose UI
    implementation (libs.material3) // Material 3 components

//    implementation (libs.androidx.foundation )// Foundation components
    implementation(libs.androidx.foundation.v168)


//    implementation (libs.androidx.foundation.layout) // Layout components
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


    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}

apply(plugin = "com.google.dagger.hilt.android")

kapt {
    correctErrorTypes = true
}