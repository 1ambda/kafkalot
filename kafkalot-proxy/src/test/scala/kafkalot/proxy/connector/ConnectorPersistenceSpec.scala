package kafkalot.proxy.connector

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import kafkalot.common.KafkalotTestSuite
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.duration._

class ConnectorPersistenceSpec extends TestKit(ActorSystem("ConnectorContainerProxySpec"))
  with ImplicitSender with KafkalotTestSuite {

  import ConnectorPersistence._
  implicit val timeout = Timeout(10 seconds)

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  test("persistence example") {
    val name = "test-connector"
    val persistence = system.actorOf(ConnectorPersistence.props(name))

    persistence ! Command.UpdateConnectorMeta(true)
    persistence ! Command.GetConnectorMeta
    expectMsgPF(10 seconds) {
      case ConnectorMeta(enabled) =>
      case _ => fail()
    }
  }
}
