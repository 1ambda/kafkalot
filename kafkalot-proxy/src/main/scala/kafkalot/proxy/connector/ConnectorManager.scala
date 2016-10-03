package kafkalot.proxy.connector

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{ Actor, ActorLogging, ActorRef, OneForOneStrategy, Props }
import akka.stream.ActorMaterializer
import akka.pattern.{ ask, pipe }
import akka.util.Timeout
import io.circe.JsonObject
import ConnectorProxyActor.{ Command => ProxyCommand }
import ConnectorPersistence.{ Command => PersistentCommand }

import scala.concurrent.duration._

class ConnectorManager()(implicit val mat: ActorMaterializer) extends Actor with ActorLogging {
  import ConnectorManager._

  import context.dispatcher
  implicit val timeout = Timeout(10 seconds)

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: Exception => Restart
    }

  // FIXME: select or create pattern
  var connectorProxy: ActorRef = _

  override def preStart() = {
    connectorProxy = context.actorOf(ConnectorProxyActor.props())
  }

  override def postStop() = {}
  override def receive = {
    case Command.GetConnectorContainerInfo =>
      (connectorProxy ? ProxyCommand.GetConnectorContainerInfo)
        .mapTo[ConnectorContainerInfo]
        .pipeTo(sender)

    case Command.UpdateConnectorMeta(name, readonly) =>
      val persistentActor = context
        .child(name)
        .getOrElse(context.actorOf(ConnectorPersistence.props(name), name))

      (persistentActor ? PersistentCommand.UpdateConnectorMeta)
        .pipeTo(sender)
  }
}

object ConnectorManager {
  def props()(implicit mat: ActorMaterializer) = Props(new ConnectorManager())

  sealed trait Command
  object Command {
    case object GetConnectorContainerInfo extends Command
    case class UpdateConnectorMeta(name: String, readonly: Boolean)
    case class UpdateConnector(name: String, config: JsonObject)
  }
}
