lazy val firepower =
  project
    .in(file("."))
    .settings(
      console / initialCommands := "import com.htmlism._"
    )
    .withCats
    .withTesting
    .aggregate(nescant, scratchpad, demo)

lazy val nescant =
  project.withCats.withTesting

lazy val scratchpad =
  project
    .withCats
    .withTesting

lazy val demo =
  module("demo")
    .withEfectMonad
    .settings(libraryDependencies += "com.htmlism" %% "rufio-zio" % "74-5cd25e9b")

ThisBuild / resolvers += "mcanlas/rufio" at "https://maven.pkg.github.com/mcanlas/rufio/"

ThisBuild / credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "mcanlas",
  sys.env("GH_PACKAGES_TOKEN")
)
