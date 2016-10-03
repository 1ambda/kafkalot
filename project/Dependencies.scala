import sbt._

object Dependencies {

  val Resolvers = Seq(
    Resolver.bintrayRepo("hseeberger", "maven")
    , "Artima Maven Repository" at "http://repo.artima.com/releases"
    , Resolver.jcenterRepo
  )

  /** plugins */
  val kindProjectorPlugin = "org.spire-math" %% "kind-projector" % "0.8.2"
  val macroParadisePlugin = "org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full
  val acyclicPlugin = "com.lihaoyi" %% "acyclic" % "0.1.4"

  /** dependencies */
  private val scalaTestVersion = "3.0.0"
  private val shapeless = "com.chuusai" %% "shapeless" % "2.3.2"


  private val akkaVersion = "2.4.11"
  private val akka = Seq(
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",

    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % "test",

    "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion % "test",
    "de.heikoseeberger" %% "akka-http-circe" % "1.10.0",
    "ch.megard" %% "akka-http-cors" % "0.1.6",
    "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.7.2",

    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,

    "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    "com.typesafe.akka" %% "akka-persistence-query-experimental" % akkaVersion,
    "org.iq80.leveldb"            % "leveldb"          % "0.7",
    "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8",

    "com.typesafe.akka" %% "akka-typed-experimental" % akkaVersion,

    "com.typesafe.akka" %% "akka-stream-kafka" % "0.11"
  )

  private val circeVersion = "0.5.2"
  private val json = Seq(
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "com.propensive" %% "rapture-json" % "2.0.0-M7"
  )

  private val catsVersion = "0.7.2"
  private val cats = Seq(
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-kernel" % catsVersion,
    "org.typelevel" %% "cats-macros" % catsVersion,
    "org.typelevel" %% "cats-free" % catsVersion,
    "org.typelevel" %% "cats-laws" % catsVersion
  )
  val Common = Seq(
    /** config */
    "com.typesafe" % "config" % "1.3.0",
    "com.iheart" %% "ficus" % "1.2.3",
    /** logging */
    "org.slf4j" % "slf4j-api" % "1.7.21",
    "ch.qos.logback" %  "logback-classic" % "1.1.7",
    /** test */
    // "org.scalactic" %% "scalactic" % scalaTestVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    /** macro */
    acyclicPlugin % "provided"
  ) ++ akka

  val Proxy = cats ++ json ++ akka
}
