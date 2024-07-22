plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("com.google.devtools.ksp")
    id("kotlin-kapt")

}

android {
    namespace = "com.example.todolistapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todolistapp"
        minSdk = 25
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity)
    ext {
        set("kotlin_version", "1.9.10")

        set("core_version", "1.12.0")
        set("appcompat_version", "1.6.1")
        set("material_version", "1.10.0")
        set("constraint_version", "2.1.4")

        set("junit_version", "4.13.2")
        set("ext_junit_version", "1.1.5")
        set("espresso_version", "3.5.1")
        set("runner_version", "1.2.0")

        set("room_version", "2.6.0")
        set("arch_lifecycle_version", "2.6.2")
        set("lifecycle_version", "2.6.1")
        set("work_version", "2.8.1")
        set("preference_version", "1.2.1")
        set("paging_version", "2.1.2")
    }

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core.ktx.v1130)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material.v1100)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.runner)

    implementation(libs.androidx.room.runtime)
    ksp("androidx.room:room-compiler:${extra["room_version"]}")
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.preference.ktx)

    implementation(libs.androidx.paging.runtime.ktx)

    androidTestUtil("androidx.test:orchestrator:1.5.0")
    androidTestImplementation(libs.kaspresso)
}