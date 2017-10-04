import Dependencies._

lazy val common = Seq(
  organization := "com.us.pryor",
  scalaVersion := "2.12.1",
  version := "1.0"
)

lazy val NumberWords = (project in file(".")).
  settings(
    common,
    name := "number-words",
    libraryDependencies += scalaTest % Test
  )
