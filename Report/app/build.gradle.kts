import com.android.build.api.dsl.Packaging

plugins {
//    kotlin("android") version "1.6.10" // Use the latest version
//    kotlin("kapt") version "1.6.10"   // Use the latest version
    id("com.android.application")

}
//apply(plugin = "kotlin-kapt")


android {
    namespace = "tdtu.report"
    compileSdk = 34

    defaultConfig {
        applicationId = "tdtu.report"
        minSdk = 24
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
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/NOTICE.md")

    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation("androidx.navigation:navigation-ui:2.7.4")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("androidx.room:room-runtime:2.4.0")
    annotationProcessor("androidx.room:room-compiler:2.4.0")
    implementation("com.sun.mail:android-mail:1.6.7")
    implementation("com.sun.mail:android-activation:1.6.7")
    implementation("com.google.dagger:hilt-android:2.38.1")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.3.1")

    implementation("androidx.room:room-rxjava2:2.4.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("androidx.lifecycle:lifecycle-reactivestreams:2.4.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
//    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")



//    implementation("com.google.android.exoplayer:exoplayer:2.15.1")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")


    //noinspection GradleCompatible
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    configurations.all {
        resolutionStrategy {
            eachDependency {
                if ((requested.group == "org.jetbrains.kotlin") && (requested.name.startsWith("kotlin-stdlib"))) {
                    useVersion("1.6.0")
                }
            }
        }
    }
}
