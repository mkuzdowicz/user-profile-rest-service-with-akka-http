
name := "user-profile-service"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++=
  Seq(
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.0-RC1",
    "com.typesafe.akka" %% "akka-http" % "10.1.0-RC1",
    "com.typesafe.akka" %% "akka-stream" % "2.5.8",
    "org.scalatest" %% "scalatest" % "3.0.3" % Test
  )

resolvers += "maven2" at "http://central.maven.org/maven2/"



        