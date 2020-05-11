lazy val root =
  project
    .in(file("."))
    .settings(
      initialCommands in console := "import com.htmlism._",
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test",
      scalafmtOnCompile := true
    )
