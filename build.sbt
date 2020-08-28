lazy val root =
  project
    .in(file("."))
    .settings(
      initialCommands in console := "import com.htmlism._",
      scalafmtOnCompile := true
    )
    .withCats
    .withTesting
    .withOrganizeImports
    .aggregate(nescant)

lazy val nescant =
  project.withCats.withTesting.withOrganizeImports
