plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    defaultConfig {
        applicationId = "com.nacarseven.series"
        minSdk = AndroidConfigVersions.minSdkVersion
        targetSdk = AndroidConfigVersions.targetSdkVersion
        versionCode = AndroidConfigVersions.defaultVersionCode
        versionName = AndroidConfigVersions.defaultVersionName

        compileSdk = AndroidConfigVersions.compileSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
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
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {

    implementation(CoreDependencies.coreKtx)
    implementation(UiDependencies.appcompat)
    implementation(UiDependencies.material)
    implementation(UiDependencies.constraintLayout)
    implementation(UiDependencies.paging)

    // Square
    implementation(CoreDependencies.retrofit)
    implementation(CoreDependencies.retrofitSerialization)
    implementation(CoreDependencies.okhttp3)

    // JetBrains
    implementation(CoreDependencies.jsonSerialization)
    implementation(CoreDependencies.coroutines)

    // Koin
    implementation(CoreDependencies.koin)

    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.androidxJunit)
    androidTestImplementation(TestDependencies.espressoCore)
}