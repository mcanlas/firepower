lazy val root =
  project
    .in(file("."))
    .settings(
      console / initialCommands := "import com.htmlism._",
      scalafmtOnCompile := true
    )
    .withCats
    .withTesting
    .aggregate(nescant)

lazy val nescant =
  project.withCats.withTesting

lazy val scratchpad =
  project
    .withCats
