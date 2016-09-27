package kafkalot.common

import com.typesafe.config.Config

trait KafkalotConfig {}
trait KafkalotConfigGen {
  def apply(config: Config): KafkalotConfig
}

