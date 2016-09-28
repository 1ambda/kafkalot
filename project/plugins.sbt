logLevel := Level.Warn

addSbtPlugin("com.dwijnand" % "sbt-dynver" % "1.0.0")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-M14")
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.2.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
addSbtPlugin("com.softwaremill.clippy" % "plugin-sbt" % "0.3.0")
// addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.0")


addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.3")
addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.4.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.0-M5")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.3.3")
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.1.0")

resolvers += Resolver.typesafeRepo("releases")
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")
