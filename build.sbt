lazy val firepower =
  project
    .in(file("."))
    .withCats
    .withTesting
    .aggregate(nescant, core, cpu, demo, playground)

lazy val nescant =
  project.withCats.withTesting

lazy val core =
  module("core")
    .withCats
    .withTesting

lazy val cpu =
  module("cpu")
    .settings(description := "CPU emulation suitable for unit testing")
    .withTesting

lazy val demo =
  module("demo")
    .dependsOn(core)
    .withEfectMonad
    .settings(libraryDependencies += "com.htmlism" %% "rufio-zio" % "76-c565ab28")
    .withGitHubPackagesCredentials
    .withResolver("rufio")

lazy val playground =
  module("playground").withCats
