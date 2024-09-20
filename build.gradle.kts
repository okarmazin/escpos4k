@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  base
  alias(libs.plugins.dependencyUpdates)

  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.vanniktechPublishingBase) apply false
  alias(libs.plugins.kotest.multiplatform) apply false
  alias(libs.plugins.detekt) apply false
}

tasks.named<Delete>("clean") {
  val buildDirs = allprojects.map { it.layout.buildDirectory }
  delete(buildDirs)
}
