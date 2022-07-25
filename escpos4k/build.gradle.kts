import com.vanniktech.maven.publish.KotlinMultiplatform

plugins {
  alias(escpos4k.plugins.kotlin.multiplatform)
  alias(escpos4k.plugins.android.library)
  alias(escpos4k.plugins.vanniktechPublishingBase)
}

group = "cz.multiplatform.escpos4k"

version = "0.1-SNAPSHOT"

mavenPublishing {
  publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.S01)
  signAllPublications()

  pom {
    name.set("EscPos4k")
    description.set("ESC/POS for Kotlin Multiplatform")
    inceptionYear.set("2022")
    url.set("https://github.com/okarmazin/escpos4k")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        id.set("okarmazin")
        name.set("Ondřej Karmazín")
        url.set("https://github.com/okarmazin")
      }
    }
    scm {
      connection.set("scm:git:git://github.com/okarmazin/escpos4k.git")
      developerConnection.set("scm:git:git://github.com/okarmazin/escpos4k.git")
      url.set("https://github.com/okarmazin/escpos4k")
    }
  }

  configure(KotlinMultiplatform())
}

kotlin {
  explicitApi()

  android { publishLibraryVariants("release") }

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
    warningsAsErrors = true
    abortOnError = false
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}
