import sbt.Keys.*
import sbt.*

object DependenciesPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    implicit class DependencyOps(p: Project) {
      def withCats: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-core" % Versions.catsCore)

      def withEfectMonad: Project =
        p.settings(libraryDependencies += "dev.zio" %% "zio" % "2.0.4")

      def withTesting: Project = {
        p.settings(
          libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest"         % "3.2.20"      % Test,
            "org.typelevel" %% "weaver-cats"       % Versions.weaver % Test,
            "org.typelevel" %% "weaver-scalacheck" % Versions.weaver % Test
          )
        )
      }
    }
  }
}
