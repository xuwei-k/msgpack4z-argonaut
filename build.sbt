import build._
import sbtcrossproject.CrossProject

val argonautVersion = "6.3.2"

val msgpack4zArgonaut = CrossProject(
  id = msgpack4zArgonautName,
  base = file(".")
)(
  JSPlatform, JVMPlatform
).crossType(
  CustomCrossType
).settings(
  Common.settings,
  scalapropsCoreSettings,
  name := msgpack4zArgonautName,
  libraryDependencies ++= (
    ("io.argonaut" %%% "argonaut" % argonautVersion) ::
    ("io.argonaut" %%% "argonaut-scalaz" % argonautVersion % "test") ::
    ("com.github.scalaprops" %%% "scalaprops" % "0.8.1" % "test") ::
    ("com.github.xuwei-k" %%% "msgpack4z-core" % "0.5.0") ::
    Nil
  ).map(_.withDottyCompat(scalaVersion.value))
).jvmSettings(
  libraryDependencies ++= (
    ("com.github.xuwei-k" %% "msgpack4z-native" % "0.3.6" % "test") ::
    ("com.github.xuwei-k" % "msgpack4z-java" % "0.3.6" % "test") ::
    ("com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0" % "test") ::
    Nil
  ).map(_.withDottyCompat(scalaVersion.value)),
  Sxr.settings
).jsSettings(
  scalacOptions ++= {
    val a = (baseDirectory in LocalRootProject).value.toURI.toString
    val g = "https://raw.githubusercontent.com/msgpack4z/msgpack4z-argonaut/" + Common.tagOrHash.value
    if (isDottyJS.value) {
      Nil
    } else {
      Seq(s"-P:scalajs:mapSourceURI:$a->$g/")
    }
  },
  scalaJSLinkerConfig ~= { _.withSemantics(_.withStrictFloats(true)) }
)

val msgpack4zArgonautJVM = msgpack4zArgonaut.jvm
val msgpack4zArgonautJS = msgpack4zArgonaut.js

val root = Project("root", file(".")).settings(
  Common.settings,
  PgpKeys.publishLocalSigned := {},
  PgpKeys.publishSigned := {},
  publishLocal := {},
  publish := {},
  publishArtifact in Compile := false,
  scalaSource in Compile := file("dummy"),
  scalaSource in Test := file("dummy")
).aggregate(
  msgpack4zArgonautJS, msgpack4zArgonautJVM
)
