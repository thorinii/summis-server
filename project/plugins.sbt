// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.3")



//resolvers += Classpaths.typesafeResolver

//resolvers += "scct-github-repository" at "http://mtkopone.github.com/scct/maven-repo"

//addSbtPlugin("reaktor" % "sbt-scct" % "0.2-SNAPSHOT")


resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "0.99.3")

addSbtPlugin("com.github.scct" %% "sbt-scct" % "0.2.1")
