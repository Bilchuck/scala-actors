import sbt._

object Dependencies {
  // Versions
  lazy val akkaVersion = "2.5.18"
  lazy val akkaHttpVersion = "10.1.5"

  // Libraries
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  val akkaHttpSpray = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

  // Projects
}