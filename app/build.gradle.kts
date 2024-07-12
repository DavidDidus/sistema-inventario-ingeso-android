plugins {
    id("com.android.application")
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.sistema_inventario_ingeso"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sistema_inventario_ingeso"
        minSdk = 26
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/io.netty.versions.properties")
        exclude("META-INF/INDEX.LIST")
    }

}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
