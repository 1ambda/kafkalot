package kafkalot.proxy.connector

import akka.actor.{ Actor, ActorLogging, Props }
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.pattern.{ ask, pipe }
import akka.stream.{ ActorMaterializer }

class ConnectorProxyActor()(implicit mat: ActorMaterializer) extends Actor with ActorLogging {
  import ConnectorProxyActor._
  import de.heikoseeberger.akkahttpcirce.CirceSupport._
  import io.circe.generic.auto._

  override def preStart() = {}
  override def postStop() = {}

  import context.dispatcher
  implicit val system = context.system

  override def receive = {
    case Command.GetConnectorContainerInfo =>

      Http()
        .singleRequest(HttpRequest(uri = "http://localhost:8083"))
        .flatMap(res => Unmarshal(res).to[ConnectorContainerInfo])
        .pipeTo(sender)

  }
}

object ConnectorProxyActor {
  def props()(implicit mat: ActorMaterializer): Props = Props(new ConnectorProxyActor())

  sealed trait Command
  object Command {
    case object GetConnectorContainerInfo extends Command
    case class GetConnector(name: String) extends Command
    case class PostConnector(name: String) extends Command
    case class DeleteConnector() extends Command
    case class UpdateConnector() extends Command
  }

}

