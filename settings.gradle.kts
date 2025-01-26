pluginManagement {
  repositories {
    gradlePluginPortal()
    // The Android Gradle Plugin comes from the Google repository, not the Gradle plugin portal.
    google()
  }
}

dependencyResolutionManagement {
  //  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    google()
  }
}

include(":escpos4k")

//include(":sample:app-android")

include(":sample:common")

rootProject.name = "EscPos4k"
