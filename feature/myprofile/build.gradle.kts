plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //id("com.google.gms.google-services") // Thêm plugin Google Services để sử dụng Firebase
    kotlin("kapt")

}

android {
    namespace = "com.mck.myprofile"
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
    implementation(project(":common:composable"))
    implementation(project(":common:theme"))
    implementation(project(":data"))

    implementation (libs.androidx.emoji2)



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
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    // Thêm thư viện Coil cho Jetpack Compose
    implementation("io.coil-kt:coil-compose:2.4.0") // Kiểm tra phiên bản mới nhất nếu cần

    // Nếu bạn cần hỗ trợ cho hình ảnh tải qua URL HTTPS, bạn có thể thêm thêm thư viện sau:
    implementation(libs.coil)

    // Firebase Authentication (nếu bạn sử dụng Firebase Auth)
    implementation(libs.firebase.auth.ktx)

    // Firebase Firestore (nếu bạn sử dụng Firestore)
    implementation(libs.google.firebase.firestore.ktx)

    // Jetpack Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Accompanist Navigation Material (for BottomSheet support)
    implementation(libs.accompanist.navigation.material)

    // Accompanist System UI Controller (for handling system UI like status bar)
    implementation(libs.accompanist.systemuicontroller)

    // Jetpack Compose UI
    implementation(libs.androidx.ui)

    // Jetpack Compose Material3 (optional, if you're using Material3)
    implementation(libs.androidx.material3)

}