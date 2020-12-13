import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    scalaVersion := "2.13.4"
  )

  object autoImport {
    implicit class ProjectOps(p: Project) {
      def withCats: Project =
        p.settings(libraryDependencies += "org.typelevel" %% "cats-core" % "2.3.0")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test")
    }
  }
}
