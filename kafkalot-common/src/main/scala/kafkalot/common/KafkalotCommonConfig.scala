package kafkalot.common

import com.typesafe.config.Config

final case class KafkalotCommonAkkaConfig(clusterSystemName: String)
final case class KafkalotCommonConfig(akka: KafkalotCommonAkkaConfig)

object KafkalotCommonConfigGen {
  import net.ceedubs.ficus.Ficus._
  import net.ceedubs.ficus.readers.ArbitraryTypeReader._

  def apply(config: Config): KafkalotCommonConfig =
    config.as[KafkalotCommonConfig]("kafkalot.common")
}

