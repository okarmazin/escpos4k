pluginManagement {
  repositories {
    gradlePluginPortal()
    // The Android Gradle Plugin comes from the Google repository, not the Gradle plugin portal.
    google()
  }
}

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
  versionCatalogs { create("escpos4k") { from(files("gradle/escpos4k.versions.toml")) } }

  repositories {
    mavenCentral()
    google()
  }
}

include(":escpos4k")

rootProject.name = "EscPos4k"
