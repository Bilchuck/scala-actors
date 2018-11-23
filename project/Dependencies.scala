import sbt._

object Dependencies {
  
  // Versions
  lazy val akkaVersion = "2.5.18"
  lazy val akkaHttpVersion = "10.1.5"
  lazy val alpakkaVersion = "0.20"
  lazy val scalaTestVersion = "3.0.5"

  // Libraries
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion
  val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % akkaVersion
  val akkaPersistence = "com.typesafe.akka" %% "akka-persistence" % akkaVersion
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j"   % akkaVersion
  val akkaStreamTestKit = "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion
  val alpakkaFile = "com.lightbend.akka" %% "akka-stream-alpakka-file" % alpakkaVersion
  val alpakkaCSV = "com.lightbend.akka" %% "akka-stream-alpakka-csv" % alpakkaVersion
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  val akkaHttpTestKit = "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion
  val akkaHttpSpray = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
  val akkaAgent = "com.typesafe.akka" %% "akka-agent" % akkaVersion
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
  val mockitoCore = "org.mockito" % "mockito-core" % "2.21.0"
  val scalaMeter = "com.storm-enroute" %% "scalameter-core" % "0.10.1"
  val playJson = "com.typesafe.play" %% "play-json" % "2.3.9"
  val logback = "ch.qos.logback" % "logback-classic" % "1.1.3"
  val databinder = "net.databinder.dispatch" %% "dispatch-core" % "0.13.4"

  // Projects
}