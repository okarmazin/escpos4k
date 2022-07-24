plugins {
  alias(escpos4k.plugins.kotlin.multiplatform)
  alias(escpos4k.plugins.android.library)
  id("maven-publish")
}

group = "cz.multiplatform.escpos4k"

version = "0.1-SNAPSHOT"

kotlin {
  explicitApi()

  android {
    publishLibraryVariants("release")
  }

  ios()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(escpos4k.arrow.core)
        implementation(escpos4k.kotlinx.coroutines.core)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(escpos4k.kotlin.test.common.annotations)
        implementation(escpos4k.kotlin.test.common.assertions)
      }
    }
    val androidMain by getting
    val iosMain by getting
  }
}

android {
  compileSdk = 31
  defaultConfig {
    minSdk = 21
    targetSdk = 31
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

  lint {
    isWarningsAsErrors = true
    isAbortOnError = false
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}
