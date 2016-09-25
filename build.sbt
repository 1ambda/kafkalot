val codename = "kafkalot"

/** settings */

val basicSettings = Seq(
  organization := s"io.github.kafkalot",
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint"),
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-encoding", "UTF-8",
    "-feature",
    "-deprecation",
    "-language:postfixOps",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-value-discard",
    "-Ybackend:GenBCode",
    "-Ydelambdafy:method",
    "-target:jvm-1.8"
  ),
  autoCompilerPlugins := true,
  addCompilerPlugin(Dependencies.acyclicPlugin),
  addCompilerPlugin(Dependencies.kindProjectorPlugin),
  addCompilerPlugin(Dependencies.macroParadisePlugin),
  resolvers ++= Dependencies.Resolvers,
  libraryDependencies ++= Dependencies.Common
)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

/** projects */

lazy val root = Project(s"${codename}", file("."))
  .settings(basicSettings)
  .settings(noPublishSettings)

lazy val common = Project("common", file(s"${codename}-common"))
  .settings(basicSettings)
  .settings(noPublishSettings)
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](
      version
      , isSnapshot
      , BuildInfoKey.action("gitSha") {
        (Process("bash" :: "-c" :: "git rev-parse HEAD || echo None" :: Nil) !!).trim }
      , scalaVersion
      , sbtVersion
    )
    , buildInfoPackage := s"${codename}.common"
    , buildInfoOptions += BuildInfoOption.BuildTime
  )

lazy val ui = Project("ui", file(s"${codename}-ui"))
  .settings(noPublishSettings)

lazy val proxy = Project("proxy", file(s"${codename}-proxy"))
  .settings(basicSettings)
  .settings(
    mainClass in Compile := Some(s"${codename}.proxy.ProxyApplication")
    , libraryDependencies ++= Dependencies.Proxy
  )
  .dependsOn(common)




    
