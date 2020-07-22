name := "play"
organization := "objektwerks"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.3"

lazy val root = (project in file("."))
                .enablePlugins(PlayScala)

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

scalacOptions += s"-Wconf:src=${baseDirectory.value.getCanonicalPath}/app/views/*.scala.html&cat=unused-imports:silent"
scalacOptions += s"-Wconf:src=${baseDirectory.value.getCanonicalPath}/target/scala-2.13/twirl/main/views/html/*.template.scala&cat=unused-imports:silent"