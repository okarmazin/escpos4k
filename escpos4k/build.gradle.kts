import com.vanniktech.maven.publish.KotlinMultiplatform

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.vanniktechPublishingBase)
  alias(libs.plugins.kotest.multiplatform)
}

group = "cz.multiplatform.escpos4k"

version = "0.4.0-SNAPSHOT"

@Suppress("UnstableApiUsage")
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
        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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
  jvmToolchain(17)
  explicitApi()

  androidTarget { publishLibraryVariants("release") }

  jvm()

  iosArm64()
  iosX64()
  iosSimulatorArm64()
  macosArm64()
  macosX64()

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.arrow.core)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.ktor.network)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(libs.kotest.assertions.core)
        implementation(libs.kotest.framework.engine)
        implementation(libs.kotest.framework.datatest)
        implementation(libs.kotest.property)
        implementation(libs.kotlin.test.common.annotations)
        implementation(libs.kotlin.test.common.assertions)
      }
    }
    val androidMain by getting
    val androidUnitTest by getting {
      dependencies {
        implementation(libs.kotlin.test.junit)
        implementation(libs.kotest.runner.junit5)
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation(libs.kotlin.test.junit)
        implementation(libs.kotest.runner.junit5)
      }
    }
  }
}

@Suppress("UnstableApiUsage")
android {
  namespace = "cz.multiplatform.escpos4k"
  compileSdk = 31
  defaultConfig {
    minSdk = 23
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

  lint {
    warningsAsErrors = true
    abortOnError = false
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  testOptions {
    unitTests.all {
      it.useJUnitPlatform()
    }
  }
}

tasks.withType<Test> {
  systemProperty("kotest.assertions.collection.print.size", 100)
}
