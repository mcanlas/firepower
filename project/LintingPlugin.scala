import sbt.Keys._
import sbt._

object LintingPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override lazy val globalSettings =
    addCommandAlias("fmt", "scalafmtAll") ++
      addCommandAlias("fix", "scalafixAll")

  object autoImport {
    // this class name should be unique to evade implicit collision with other ops classes
    implicit class LintingOps(p: Project) {
      def withOrganizeImports: Project = {
        import scalafix.sbt.ScalafixPlugin.autoImport._

        p.settings(
          ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.0",
          semanticdbEnabled := true,
          semanticdbVersion := scalafixSemanticdb.revision
        )
      }
    }
  }
}
