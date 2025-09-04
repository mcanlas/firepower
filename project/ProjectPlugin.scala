import sbt.Keys.*
import sbt.*

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
        p.settings(libraryDependencies += "org.typelevel" %% "cats-core" % "2.13.0")

      def withEfectMonad: Project =
        p.settings(libraryDependencies += "dev.zio" %% "zio" % "2.0.4")

      def withTesting: Project = {
        val weaverVersion =
          "0.10.1"

        p.settings(
          libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest"         % "3.2.19"      % Test,
            "org.typelevel" %% "weaver-cats"       % weaverVersion % Test,
            "org.typelevel" %% "weaver-scalacheck" % weaverVersion % Test
          )
        )
      }
    }
  }
}
