plugins { base }

tasks.named<Delete>("clean") {
  val buildDirs = allprojects.map(Project::getBuildDir)
  delete(buildDirs)
}
