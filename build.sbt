import sbt._
import Keys._
import Dependencies._

/**
  Tasks
  https://www.scala-sbt.org/0.13/docs/Basic-Def.html
  Custom keys
  * Define a key to new task for example; key named "hello"
  * Note: Typically, lazy vals are used instead of vals to avoid initialization order problems.

  TaskKey[File] -- task returns jar file it creates
*/
lazy val helloTask = taskKey[Unit]("An example task")  // Unit = void

/** 
  Task graph
  https://www.scala-sbt.org/0.13/docs/Task-Graph.html
*/
lazy val scalacOptions = taskKey[Seq[String]]("Options for the Scala compiler.")
lazy val update = taskKey[UpdateReport]("Resolves and optionally retrieves dependencies, producing a report.")
lazy val clean = taskKey[Unit]("Deletes files produced by the build, such as generated sources, compiled classes, and task caches.")

/*
scalacOptions := {
  val ur = update.value  // update task happens-before scalacOptions
  val x = clean.value    // clean task happens-before scalacOptions
  // ---- scalacOptions begins here ----
  ur.allConfigurations.take(3)
}
*/

/**
  Library dependencies
 */
val derby = "org.apache.derby" % "derby" % "10.4.1.3"

lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.5"
)

// or below since the val is the same as the directory name
// lazy val core = project
// lazy val util = (project in file("util"))
//   .settings(
//     commonSettings,
//     // other settings
//   )

/**
  root stuff
 */
// lazy val root = (project in file(".")).
//   settings(
//     // inThisBuild(List(
//     //   organization := "com.example",
//     //   scalaVersion := "2.12.5",
//     //   version      := "0.1.0-SNAPSHOT"
//     // )),
//     commonSettings,
//     name := "Hello",
//     libraryDependencies += scalaTest % Test,

//     helloTask := { println("Hello Task!") },

//     scalacOptions := {
//       val out = streams.value // streams task happens-before scalacOptions
//       val log = out.log
//       log.info("--> 123")
//       val ur = update.value   // update task happens-before scalacOptions
//       log.info("--> 456")
//       ur.allConfigurations.take(3)
//     },

//     libraryDependencies += derby
//   )

lazy val root = (project in file("."))
  .aggregate(lib)
  .dependsOn(lib)
  .settings(commonSettings)
  .settings(
    name := "Hello",

    helloTask := { println("Hello Task!") },

    libraryDependencies += scalaTest % Test,

    scalacOptions := {
      val out = streams.value // streams task happens-before scalacOptions
      val log = out.log
      log.info("--> 123")
      val ur = update.value   // update task happens-before scalacOptions
      log.info("--> 456")
      // ur.allConfigurations.take(3)
      ur.allConfigurations.map(_.toString)
    }   
  )

lazy val lib = project
  .settings(commonSettings)

/*
lazy val core = (project in file("core"))
  .settings(commonSettings)
  .dependsOn(util)
*/