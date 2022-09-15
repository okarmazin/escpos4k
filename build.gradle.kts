@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  base
  alias(libs.plugins.dependencyUpdates)
}

tasks.named<Delete>("clean") {
  val buildDirs = allprojects.map(Project::getBuildDir)
  delete(buildDirs)
}
