package kafkalot.proxy.connector

import akka.actor.{ ActorLogging, Props }
import akka.persistence.{ PersistentActor, SnapshotOffer }
import kafkalot.proxy.connector.ConnectorPersistence.Event.ConnectorUpdated

class ConnectorPersistence(connectorName: String) extends PersistentActor with ActorLogging {

  import ConnectorPersistence._

  override def persistenceId: String = s"connector:${connectorName}"

  override def receiveRecover: Receive = {
    case event: Event => updateState(event)
    case SnapshotOffer(_, snapshot: State) => state = snapshot
  }

  var state = State(connectorName, false)

  def updateState(event: Event): Unit = {
    event match {
      case ConnectorUpdated(readonly) =>
        state = state.copy(readonly = readonly)
    }
  }

  override def receiveCommand: Receive = {
    case Command.UpdateConnectorMeta(readonly) =>
      persist(Event.ConnectorUpdated(readonly)) { event =>
        updateState(event)
      }

    case Command.GetConnectorMeta =>
      sender ! ConnectorMeta(state.readonly)
  }
}

object ConnectorPersistence {
  def props(connectorName: String): Props = Props(new ConnectorPersistence(connectorName))
  case class State(name: String, readonly: Boolean)

  sealed trait Command
  object Command {
    case class UpdateConnectorMeta(readonly: Boolean) extends Command
    case object GetConnectorMeta extends Command
  }

  sealed trait Event
  object Event {
    case class ConnectorUpdated(readonly: Boolean) extends Event
  }
}

