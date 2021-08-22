name := "play"
organization := "objektwerks"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.6"
lazy val root = (project in file("."))
                .enablePlugins(PlayScala)
libraryDependencies ++= {
  val silencerVersion = "1.7.5"
  Seq(
    compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
    "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full,
    guice,
    ws,
    "org.playframework.anorm" %% "anorm" % "2.6.10" % Test,
    "com.h2database" % "h2" % "1.4.200" % Test,
    "com.typesafe" % "config" % "1.4.0" % Test,
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
  )
}
scalacOptions ++= Seq(
  "-P:silencer:pathFilters=app/views/.*",
  "-P:silencer:pathFilters=target/.*",
  s"-P:silencer:sourceRoots=${baseDirectory.value.getCanonicalPath}"
)
