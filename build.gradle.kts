plugins {
  base
  alias(escpos4k.plugins.dependencyUpdates)
}

tasks.named<Delete>("clean") {
  val buildDirs = allprojects.map(Project::getBuildDir)
  delete(buildDirs)
}
