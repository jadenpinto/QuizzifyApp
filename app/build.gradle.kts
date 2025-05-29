plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // SQLite DB:
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "uk.ac.aber.dcs.cs31620.quizzify"
    compileSdk = 34

    defaultConfig {
        applicationId = "uk.ac.aber.dcs.cs31620.quizzify"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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
        freeCompilerArgs +=
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }
    buildFeatures {
        compose = true
    }
//    Not required for Kotlin 2.0
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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

    // To use icons that are not in the basic set
    implementation(libs.androidx.material.icons.extended)

    // To use Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // To constraint layout
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.constraintlayout.compose)

    // SQLite DB:
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.testing)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.glide.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}