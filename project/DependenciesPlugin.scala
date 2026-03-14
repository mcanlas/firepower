import sbt.Keys.*
import sbt.*

object DependenciesPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    implicit class DependencyOps(p: Project) {
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
