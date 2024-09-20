@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "escpos4k.sample.app"
  compileSdk = libs.versions.androidCompileSdk.get().toInt()
  defaultConfig {
    minSdk = libs.versions.androidMinSdk.get().toInt()
    targetSdk = libs.versions.androidTargetSdk.get().toInt()
    multiDexEnabled = true
  }

  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    getByName("release") {
      isMinifyEnabled = true
      signingConfig = signingConfigs.findByName("debug")
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      rootProject.ext.set("escpos4k.enableComposeCompilerReports", "true")
    }
  }

  productFlavors {
    flavorDimensions += "requiredDimension"
    register("development") { versionNameSuffix = "-development" }
    register("production")
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

kotlin { jvmToolchain(17) }

dependencies {
  coreLibraryDesugaring(libs.androidDesugaring)
  implementation(project(":sample:common"))
}
