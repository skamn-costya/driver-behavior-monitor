plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.driverbehaviormonitor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.driverbehaviormonitor"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    kotlin {
        jvmToolchain(21)
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)

    // Jetpack Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.livedata)
//    implementation("androidx.compose.runtime:runtime-livedata:1.6.8")

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)




    // retrofit
    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.squareup.retrofit2.converter.gson)
    implementation(libs.com.google.zxing)

    // Skoda
    implementation(libs.vwg.skoda.maulcompose.core)
    implementation(libs.vwg.skoda.maulcompose.legacy)

//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
//    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

//    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.4")
//    implementation("com.google.code.gson:gson:2.10.1")


    // Debug & tests
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
}
