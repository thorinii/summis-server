import play.Project._

name := """summis-server"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.2.0",
  "org.webjars" % "bootstrap" % "3.1.1",
  "joda-time" % "joda-time" % "2.3"
)

playScalaSettings


resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                  "staging"       at "http://oss.sonatype.org/content/repositories/staging",
                  "releases"      at "http://oss.sonatype.org/content/repositories/releases"
                 )


scalacOptions += "-feature"


scoverage.ScoverageSbtPlugin.instrumentSettings


seq(ScctPlugin.instrumentSettings : _*)
