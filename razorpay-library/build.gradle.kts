plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.techlambda.razorpay_library"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

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
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            buildConfigField("String", "API_KEY", "\"rzp_test_PVAV1M6qu9yQa7\"")
            manifestPlaceholders["API_KEY"] = "rzp_test_PVAV1M6qu9yQa7"
        }

        create("prod") {
            dimension = "environment"
            buildConfigField("String", "API_KEY", "\"rzp_live_CBIHWFxE2coQ6l\"")
            manifestPlaceholders["API_KEY"] = "rzp_live_CBIHWFxE2coQ6l"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api("com.razorpay:checkout:1.6.40")
    implementation("com.github.solutionsaint:auth-module:1.2.6")
    implementation("com.github.solutionsaint:common-library:1.0.2")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("prodRelease") {
                from(components["prodRelease"])
                groupId = "com.github.solutionsaint"
                artifactId = "razorpay-library-prod"
                version = "1.0.0"
            }
            create<MavenPublication>("devRelease") {
                from(components["devRelease"])
                groupId = "com.github.solutionsaint"
                artifactId = "razorpay-library-dev"
                version = "1.0.0"
            }
        }
    }
}