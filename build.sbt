val finchVersion = "0.26.1"
val circeVersion = "0.10.1"
val scalatestVersion = "3.0.5"
val mongoDbDriverVersion = "4.1.0"

lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "processor-api-scala",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.7",
    libraryDependencies ++= Seq(
      "com.github.finagle" %% "finchx-core"  % finchVersion,
      "com.github.finagle" %% "finchx-circe"  % finchVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "org.scalatest"      %% "scalatest"    % scalatestVersion % "test",
      "org.mongodb.scala" %% "mongo-scala-driver" % mongoDbDriverVersion,
      "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
      "org.apache.logging.log4j" % "log4j-core" % "2.13.3" % Runtime
    )
  )