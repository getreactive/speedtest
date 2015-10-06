name := """speedtest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "requirejs" % "2.1.14-1",
  "org.webjars" % "jquery" % "1.11.1",
  "org.webjars" % "bootstrap" % "3.3.5" exclude("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.4.1" exclude("org.webjars", "jquery"),
  "org.webjars" % "angular-material" % "0.10.1-rc3",
  "org.webjars" % "underscorejs" % "1.8.3" exclude("org.webjars", "jquery"),
  "org.webjars" % "d3js" % "3.5.5-1",
  "org.webjars" % "dygraphs" % "1.1.1",
  "org.webjars.bower" % "angular-material-icons" % "0.5.0",
  "org.webjars.bower" % "material-design-iconic-font" % "2.1.1",
  "org.webjars" % "ngDialog" % "0.3.12-1",
  "org.webjars.bower" % "angular-local-storage" % "0.2.2"
)

libraryDependencies += "org.apache.thrift" % "libthrift" % "0.9.2"

libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.12"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
