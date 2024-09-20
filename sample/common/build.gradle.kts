import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
}

kotlin {
  jvmToolchain(17)
  explicitApi()

  androidTarget { publishLibraryVariants("release") }
  val xcfName = "EscPos4kShared"
  val xcf = XCFramework(xcfName)
  listOf(iosX64(), iosArm64(), iosSimulatorArm64(), macosArm64(), macosX64()).forEach {
    it.binaries.framework {
      //      export(libs.moko.graphics)
      //      export(libs.moko.resources)
      baseName = xcfName
      xcf.add(this)
    }
  }

  sourceSets {
    val commonMain by getting { dependencies { implementation(project(":escpos4k")) } }
  }
}

android {
  namespace = "escpos4k.sample.common"
  compileSdk = libs.versions.androidCompileSdk.get().toInt()
  defaultConfig {
    minSdk = libs.versions.androidMinSdk.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}
