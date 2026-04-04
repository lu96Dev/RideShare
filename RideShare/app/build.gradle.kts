import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.rideshare"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.rideshare"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // --- INICIO CONFIGURACIÓN API KEY (Sintaxis Kotlin DSL) ---
        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(FileInputStream(localPropertiesFile))
        }
        // Creamos la variable segura
        buildConfigField("String", "MAPS_API_KEY", "\"${properties.getProperty("MAPS_API_KEY")}\"")

        // Pasar la clave al AndroidManifest
        manifestPlaceholders["MAPS_API_KEY"] = properties.getProperty("MAPS_API_KEY") ?: ""

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // ¡SÚPER IMPORTANTE! Sin esto, BuildConfig.MAPS_API_KEY no existirá en tu Java
    buildFeatures {
        buildConfig = true
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
}

dependencies {

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Librería para mostrar mapas
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    // ¡NUEVA! Librería oficial de Google para el buscador de lugares (Autocomplete)
    implementation("com.google.android.libraries.places:places:3.3.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}