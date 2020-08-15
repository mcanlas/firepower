import sbt.Keys._
import sbt._

object ProjectPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    scalaVersion := "2.13.3"
  )

  object autoImport {
    implicit class ProjectOps(p: Project) {
      def withCats: Project =
        p
          .settings(libraryDependencies += "org.typelevel" %% "cats-core" % "2.2.0-RC2")

      def withTesting: Project =
        p.settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.1" % "test")

      def withTestingBeta: Project =
        p.settings(
          libraryDependencies += "com.disneystreaming" %% "weaver-framework" % "0.4.2" % Test,
          testFrameworks += new TestFramework("weaver.framework.TestFramework"))
    }
  }
}
