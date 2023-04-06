import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  val autoImport = ThingsToImport

  object ThingsToImport {
    private def jarName(s: String) =
      "firepower-" + s

    def module(s: String): Project =
      Project(s, file(jarName(s)))
        .settings(name := jarName(s))

    implicit class ProjectOps(p: Project) {
      def withCats: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0")

      def withEfectMonad: Project =
        p.settings(libraryDependencies += "dev.zio" %% "zio" % "2.0.4")

      def withTesting: Project = {
        val weaverVersion =
          "0.8.2"

        p.settings(
          testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
          libraryDependencies ++= Seq(
            "org.scalatest"       %% "scalatest"         % "3.2.15"      % Test,
            "com.disneystreaming" %% "weaver-cats"       % weaverVersion % Test,
            "com.disneystreaming" %% "weaver-scalacheck" % weaverVersion % Test
          )
        )
      }
    }
  }
}
