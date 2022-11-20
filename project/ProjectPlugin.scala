import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  val autoImport = ThingsToImport

  object ThingsToImport {
    implicit class ProjectOps(p: Project) {
      def withCats: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0")

      def withTesting: Project = {
        val weaverVersion =
          "0.8.0"

        p.settings(
          testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
          libraryDependencies ++= Seq(
            "org.scalatest"       %% "scalatest"         % "3.2.14"      % Test,
            "com.disneystreaming" %% "weaver-cats"       % weaverVersion % Test,
            "com.disneystreaming" %% "weaver-scalacheck" % weaverVersion % Test
          )
        )
      }
    }
  }
}
