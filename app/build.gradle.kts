import org.jetbrains.kotlin.cli.js.internal.main
import java.util.Properties
import java.io.FileInputStream
import java.net.URL
import java.nio.file.Files

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.nabil.webviewandroid"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = System.getenv("PKG_NAME")
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String","WEB_URL", "\"${System.getenv("WEB_URL")}\"")
        resValue("string","APP_LABEL","\"${System.getenv("APP_LABEL")}\"")

    }

    signingConfigs {
        create("release") {
            val keystoreProperties = Properties().apply {
                val keystorePropertiesFile = rootProject.file("keystore.properties.kts")
                if (keystorePropertiesFile.exists()) {
                    load(FileInputStream(keystorePropertiesFile))
                }
            }
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }

    applicationVariants.all { variant ->
        variant.outputs.all { output ->

            output.processManifest.doLast(){
                val iconLink : String = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Sign-check-icon.png/768px-Sign-check-icon.png"
                val iconFile = File("${buildDir}/res/drawable", "ic_launcher_custom.png")
                File("${buildDir}/res/drawable").mkdirs()

                URL(iconLink).openStream().use { input ->
                    Files.newOutputStream(iconFile.toPath()).use { output ->
                        input.copyTo(output)
                    }
                }

                android.defaultConfig.vectorDrawables.useSupportLibrary = true
                android.productFlavors.forEach {
                    it.vectorDrawables.useSupportLibrary = true
                }

                sourceSets {
                    getByName("main").res.srcDir(File("${buildDir}/res"))
                }

            }
            false
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }

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
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


