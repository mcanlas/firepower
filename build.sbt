lazy val firepower =
  project
    .in(file("."))
    .settings(
      console / initialCommands := "import com.htmlism._"
    )
    .withCats
    .withTesting
    .aggregate(nescant, scratchpad)

lazy val nescant =
  project.withCats.withTesting

lazy val scratchpad =
  project
    .withCats
    .withTesting
