import java.util.Properties
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.io.IOException

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

val downloadIcon by tasks.registering(DownloadIconTask::class) {
    imageUrl.set(System.getenv("Icon_Uri"))
    destinationPath.set("${project.projectDir}/src/main/res/mipmap-anydpi-v26/ic_launcher.xml")
}

tasks.named("preBuild").configure {
    dependsOn(downloadIcon)
}

open class DownloadIconTask : DefaultTask() {
    @Input
    val imageUrl: Property<String> = project.objects.property(String::class.java)

    @Input
    val destinationPath: Property<String> = project.objects.property(String::class.java)

    @TaskAction
    fun downloadIcon() {
        val connection = URL(imageUrl.get()).openConnection() as HttpURLConnection
        connection.connect()

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            connection.inputStream.use { input ->
                File(destinationPath.get()).outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        } else {
            throw IOException("Failed to download image: HTTP ${connection.responseCode} ${connection.responseMessage}")
        }

        connection.disconnect()
    }
}

