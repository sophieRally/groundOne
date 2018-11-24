import sbt._
import Keys._

object Common {
  val settings: Seq[Setting[_]] = Seq(
    organization := "com.example",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.5"
  )

  val fooDependency = "com.foo" %% "foo" % "2.4"
}