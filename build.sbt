scalaVersion := "3.3.3"

lazy val scala = project.in(file(".")).
  enablePlugins(ScalaJSPlugin /*, ScalaJSBundlerPlugin */).
  settings(
    scalaJSUseMainModuleInitializer := true,
    // scalaJSMainModuleInitializer := Some("nineclue.Test"),
    mainClass := Some("nineclue.Test"),
    libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "2.2.0",
        "com.lihaoyi" %%% "scalatags" % "0.12.0",
    ),
    Compile / scalaJSLinkerConfig ~= { _.withSourceMap(false) },
  )

lazy val fastCopy = taskKey[Unit]("")
fastCopy := {
  val _ = (Compile / fastOptJS).value
  val projectName = projectID.value.toString().takeWhile(_ != ':')
  val fastJS = s"${baseDirectory.value}/target/scala-${scalaVersion.value}/scala-fastopt/main.js"
  val targetJS = s"${baseDirectory.value}/src/${projectName}-bundle.js"
  IO.copyFile(file(fastJS), file(targetJS))
}

lazy val fullCopy = taskKey[Unit]("")
fullCopy := {
  val _ = (Compile / fullOptJS).value
  val projectName = projectID.value.toString().takeWhile(_ != ':')
  val fastJS = s"${baseDirectory.value}/target/scala-${scalaVersion.value}/scala-opt/main.js"
  val targetJS = s"${baseDirectory.value}/src/${projectName}-bundle.js"
  IO.copyFile(file(fastJS), file(targetJS))
}
