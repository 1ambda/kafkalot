package kafkalot.proxy.connector

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.{ ImplicitSender, TestKit }
import kafkalot.common.KafkalotTestSuite

import scala.concurrent.duration._

class ConnectorProxySpec
    extends TestKit(ActorSystem("ConnectorContainerProxySpec"))
    with ImplicitSender with KafkalotTestSuite {

  import ConnectorProxyActor._

  implicit val materializer = ActorMaterializer()

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  test("GetConnectorContainerInfo") {
    val proxy = system.actorOf(ConnectorProxyActor.props())
    proxy ! Command.GetConnectorContainerInfo

    expectMsgPF(10 seconds) {
      case ConnectorContainerInfo(version, commit) =>
      case _ => fail()
    }
  }
}
