import build._

Common.settings

scalapropsSettings

name := msgpack4zArgonautName

scalapropsVersion := "0.3.4"

val argonautVersion = "6.2-RC1"

libraryDependencies ++= (
  ("io.argonaut" %% "argonaut" % argonautVersion) ::
  ("io.argonaut" %% "argonaut-scalaz" % argonautVersion % "test") ::
  ("com.github.xuwei-k" %% "msgpack4z-core" % "0.3.5") ::
  ("com.github.xuwei-k" % "msgpack4z-java" % "0.3.4" % "test") ::
  ("com.github.xuwei-k" % "msgpack4z-java06" % "0.2.0" % "test") ::
  ("com.github.xuwei-k" %% "msgpack4z-native" % "0.3.1" % "test") ::
  Nil
)

Sxr.settings