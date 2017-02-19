name := """play-scala-js-demo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).aggregate(client)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

lazy val client = (project in file("client")).settings(
	scalaVersion := "2.11.7"
).enablePlugins(ScalaJSPlugin)

lazy val copyjs = TaskKey[Unit]("copyjs", "Copy javascript files to target directory")
copyjs := {
  val outDir = baseDirectory.value / "public/javascripts"
	val inDir = baseDirectory.value / "client/target/scala-2.11"
	val files = Seq("client-fastopt.js", "client-fastopt.js.map", "client-jsdeps.js") map { p =>   (inDir / p, outDir / p) }
	IO.copy(files, true)
}

addCommandAlias("runWithJS", ";fastOptJS;copyjs;run")
