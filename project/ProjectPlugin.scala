import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  val autoImport = ThingsToImport

  object ThingsToImport {
    implicit class ProjectOps(p: Project) {
      def withCats: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test")
    }
  }
}
