import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.android)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.androiddriver)

        }
        commonMain.dependencies {
            implementation("io.insert-koin:koin-core:4.0.0")
            implementation("io.insert-koin:koin-compose:4.0.0")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

            implementation("com.arkivanov.decompose:decompose:3.0.0")
            implementation("com.arkivanov.decompose:extensions-compose:3.0.0")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)
            implementation(libs.kotlin.json)
            implementation(libs.kotlin.dateTime)
            implementation(libs.kotlin.stdlib)

            implementation(libs.ktor.core)
            implementation(libs.ktor.contentnegotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.json)


        }

        iosMain.dependencies {
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.iosdriver)
            implementation(libs.ktor.darwin)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

sqldelight {
    databases {
        create("MicrosDB") {
            packageName.set("com.xcvi.micros")
        }
    }
}

android {
    namespace = "com.xcvi.micros"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.xcvi.micros"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

