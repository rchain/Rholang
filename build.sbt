import BNFC._

lazy val commonOptions = Seq(
  "-language:existentials",
  "-language:higherKinds",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused-import",
  "-Ywarn-value-discard",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked")

lazy val consoleOptions = commonOptions diff Seq("-Ywarn-unused-import")

lazy val commonSettings = Seq(
  name := "rholang",
  organization := "com.synereo",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.10.6", scalaVersion.value),
  scalacOptions := commonOptions,
  scalacOptions in (Compile, console) := consoleOptions,
  scalacOptions in (Test, console) := consoleOptions)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(bnfcSettings: _*)
