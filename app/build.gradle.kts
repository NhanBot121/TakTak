import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.mck.taktak"
    compileSdk = 34


//    fun Packaging.() {
//        resources.excludes.add("META-INF/**/*")
//    }

    defaultConfig {
        applicationId = "com.mck.taktak"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    configurations.all {
        resolutionStrategy {
            force("androidx.emoji2:emoji2:1.5.0")
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":common:theme"))
    implementation(project(":common:composable"))
    implementation(project(":feature:discovery"))
    implementation(project(":feature:home"))
    implementation(project(":feature:inbox"))
    implementation(project(":feature:myprofile"))
    implementation(project(":feature:cameramedia"))

//    implementation (libs.play.services)


    // CameraX dependencies
    implementation("androidx.camera:camera-camera2:1.4.0") // Camera2 implementation
    implementation("androidx.camera:camera-lifecycle:1.4.0") // Lifecycle integration
    implementation("androidx.camera:camera-view:1.4.0") // For the camera preview view




    implementation (libs.androidx.emoji2)
    implementation(libs.androidx.emoji2.views)
    implementation(libs.androidx.emoji2.views.helper)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(libs.firebase.analytics)


    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

    // Jetpack Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Accompanist Navigation Material (for BottomSheet support)
//    implementation(libs.accompanist.navigation.material)

//    implementation(libs.material.navigation)

    // Accompanist System UI Controller (for handling system UI like status bar)
    implementation(libs.accompanist.systemuicontroller)

    // Jetpack Compose UI
    implementation(libs.androidx.ui)

    // Jetpack Compose Material3 (optional, if you're using Material3)
    implementation(libs.androidx.material3)

    // Jetpack Compose tooling
    debugImplementation(libs.androidx.ui.tooling.preview)

    // Jetpack Compose Material (for ModalBottomSheet, SwipeableDefaults, etc.)
    implementation(libs.androidx.material) // For ModalBottomSheetValue, SwipeableDefaults, etc.

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
}

kapt {
    correctErrorTypes = true
}