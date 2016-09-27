package kafkalot.proxy

import com.typesafe.config.Config
import kafkalot.common.{ KafkalotConfigGen, KafkalotConfig }

final case class ProxyConfig(app: ProxyAppConfig) extends KafkalotConfig
final case class ProxyAppConfig(port: Int, host: String)

object ProxyConfigGen extends KafkalotConfigGen {
  import net.ceedubs.ficus.Ficus._
  import net.ceedubs.ficus.readers.ArbitraryTypeReader._

  override def apply(config: Config) =
    config.as[ProxyConfig]("kafkalot.proxy")
}
