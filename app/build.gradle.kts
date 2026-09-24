import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

val localProperties = Properties().apply {
    val archivo = rootProject.file("local.properties")

    if (archivo.exists()) {
        load(FileInputStream(archivo))
    }
}

val mapsApiKey = localProperties
    .getProperty("MAPS_API_KEY", "")


android {
    namespace = "com.example.sippina_aplicacion"
    compileSdk {
        version = release(37)
    }


    defaultConfig {
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
    }


    defaultConfig {
        applicationId = "com.example.sippina_aplicacion"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime.saveable)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.ui.graphics)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material.icons.extended)
    // Servicio de Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)

    // Google Maps
    implementation(libs.google.maps.compose)
    implementation(libs.google.play.services.maps)

    // Servicios de ubicación
    implementation(libs.google.play.services.location)

    // Manejo de permisos en Android
    implementation(libs.accompanist.permissions)

}