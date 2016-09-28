package kafkalot.proxy

import com.typesafe.config.Config
import kafkalot.common.{ KafkalotConfig }

final case class ProxyConfig(app: ProxyAppConfig) extends KafkalotConfig
final case class ProxyAppConfig(port: Int, host: String)

object ProxyConfig {
  import net.ceedubs.ficus.Ficus._
  import net.ceedubs.ficus.readers.ArbitraryTypeReader._

  def fromConfig(config: Config) =
    config.as[ProxyConfig]("kafkalot.proxy")
}
