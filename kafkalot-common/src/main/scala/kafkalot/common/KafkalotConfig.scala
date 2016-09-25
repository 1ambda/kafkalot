package kafkalot.common

import com.typesafe.config.Config

trait KafkalotConfig {}
trait KafkalotConfigGenerator {
  def apply(config: Config): KafkalotConfig
}
