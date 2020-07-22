name := "play"
organization := "objektwerks"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.3"

lazy val root = (project in file("."))
                .enablePlugins(PlayScala)

libraryDependencies ++= {
  val silencerVersion = "1.7.0"
  Seq(
    compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
    "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full,
    guice,
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
  )
}

scalacOptions ++= Seq(
  "-P:silencer:pathFilters=app/views/.*",
  "-P:silencer:pathFilters=target/.*",
  s"-P:silencer:sourceRoots=${baseDirectory.value.getCanonicalPath}"
)