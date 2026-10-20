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
    // Los puse porque fallaban los test para android con compose
    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.benchmark.traceprocessor)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.remote.creation.core)
    implementation(libs.androidx.compose.runtime.saveable)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.play.services.cast.framework)
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

    // Este es para Coil
    implementation("io.coil-kt:coil-compose:2.6.0")


    // Necesario para el backend
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    implementation(libs.google.play.services.location)

    // Estas implementaciones son herramientas para realizar testin en Android Studio

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation("app.cash.turbine:turbine:1.1.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")


    // Implementaciones de test de Android para la aplicacion
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")


}

tasks.withType<Test> {
    useJUnit()
    failOnNoDiscoveredTests = false
    filter {
        isFailOnNoMatchingTests = false
    }
}