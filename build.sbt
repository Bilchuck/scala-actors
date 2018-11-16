import Dependencies._

ThisBuild / version := "0.1"
ThisBuild / organization := "ua.edu.ucu"
ThisBuild / scalaVersion := "2.12.7"
ThisBuild / libraryDependencies ++= Seq(
  "com.storm-enroute" %% "scalameter-core" % "0.10.1"
)

lazy val root = (project in file("."))
  .settings(name := "streaming-ucu-week3")
  .aggregate(week3_main, week3_home)

lazy val week3_main = (project in file("main"))
    .settings(
      libraryDependencies ++= Seq(
        akkaStream, akkaHttp, akkaActor, akkaHttpSpray,
      )
    )
lazy val week3_home = project in file("home")