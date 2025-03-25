name := "play"
organization := "objektwerks"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.16"
lazy val root = (project in file("."))
                .enablePlugins(PlayScala)
libraryDependencies ++= {
  val silencerVersion = "1.7.19"
  Seq(
    compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
    "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full,
    guice,
    ws,
    "org.playframework.anorm" %% "anorm" % "2.7.0" % Test,
    "com.h2database" % "h2" % "2.3.232" % Test,
    "com.typesafe" % "config" % "1.4.3" % Test,
    "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
  )
}
scalacOptions ++= Seq(
  "-P:silencer:pathFilters=app/views/.*",
  "-P:silencer:pathFilters=target/.*",
  s"-P:silencer:sourceRoots=${baseDirectory.value.getCanonicalPath}"
)
