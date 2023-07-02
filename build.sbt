ThisBuild / organization := "com.tlmurphy"

lazy val adventZio = (project in file("advent-zio"))
  .settings(
    name := "advent-zio",
    scalaVersion := "3.3.0",
    version := "1.0",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.15",
      "dev.zio" %% "zio-streams" % "2.0.15"
    )
  )

lazy val adventCats = (project in file("advent-cats"))
  .settings(
    name := "advent-cats",
    scalaVersion := "3.3.0",
    version := "1.0",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.5.1",
      "co.fs2" %% "fs2-core" % "3.7.0",
      "co.fs2" %% "fs2-io" % "3.7.0"
    )
  )
