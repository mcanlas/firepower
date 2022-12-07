lazy val firepower =
  project
    .in(file("."))
    .withCats
    .withTesting
    .aggregate(nescant, core, demo)

lazy val nescant =
  project.withCats.withTesting

lazy val core =
  module("core")
    .withCats
    .withTesting

lazy val demo =
  module("demo")
    .dependsOn(core)
    .withEfectMonad
    .settings(libraryDependencies += "com.htmlism" %% "rufio-zio" % "76-c565ab28")

ThisBuild / resolvers += "mcanlas/rufio" at "https://maven.pkg.github.com/mcanlas/rufio/"

ThisBuild / credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "mcanlas",
  sys.env("GH_PACKAGES_TOKEN")
)
