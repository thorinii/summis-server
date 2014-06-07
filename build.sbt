name := """summis-server"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.1.1",
  "joda-time" % "joda-time" % "2.3",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  jdbc, anorm,
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)


resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                  "staging"       at "http://oss.sonatype.org/content/repositories/staging",
                  "releases"      at "http://oss.sonatype.org/content/repositories/releases"
                 )


scalacOptions += "-feature"


scoverage.ScoverageSbtPlugin.instrumentSettings


// seq(ScctPlugin.instrumentSettings : _*)



TwirlKeys.templateImports ++= Seq(
  "me.lachlanap._",
  "me.lachlanap.summis.logic._"
)
