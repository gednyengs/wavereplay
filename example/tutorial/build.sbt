ThisBuild / organization    := "com.example"
ThisBuild / version         := "1.0.0"
ThisBuild / scalaVersion    := "2.13.14"

lazy val root = (project in file("."))
    .settings(
        name := "tutorial",
        libraryDependencies ++= Seq(
            "com.sekekama" %% "wavereplay" % "1.0.0"
        )
    )
