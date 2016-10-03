package kafkalot.proxy.connector

trait ConnectorModel {}

case class ConnectorContainerInfo(version: String, commit: String)
case class ConnectorMeta(readonly: Boolean)
