ThisBuild / scalaVersion        := "2.13.14"
ThisBuild / version             := "1.0.0"
ThisBuild / organization        := "com.sekekama"

lazy val root = (project in file("."))
    .settings(
        name := "wavereplay",
        libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest" % "3.2.11"
        )
    )
