package kafkalot.proxy

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import kafkalot.common.{ KafkalotApplication, KafkalotCommonConfig }
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }
import scala.util.{ Failure, Success }

class ProxyApplication(val config: ProxyConfig)(
    protected implicit val system: ActorSystem,
    private implicit val mat: ActorMaterializer
) extends KafkalotApplication with ProxyService {
  private implicit val executionContext = system.dispatcher
  protected val logger = Logging(system, getClass)
  override val applicationName: String = "gateway"

  private var bindingFuture: Future[Http.ServerBinding] = _

  override def preStart() = {
    bindingFuture = Http().bindAndHandle(createHttpHandler, config.app.host, config.app.port)

    bindingFuture onComplete {
      case Success(b) =>
        println(s"Server started, listening on: ${b.localAddress}")
      case Failure(e) =>
        println(s"Server could not be bound to ${config.app.host}:${config.app.port}. (${e.getMessage})")
    }
  }

  override def preStop() = {
    Await.result(bindingFuture.flatMap(_.unbind()), Duration.Inf)
  }
}

object ProxyApplication extends App {
  val rawCfg = ConfigFactory.load()
  val commonCfg = KafkalotCommonConfig.fromConfig(rawCfg)
  val proxyCfg = ProxyConfig.from(rawCfg)

  private implicit val system = ActorSystem(commonCfg.akka.clusterSystemName)
  private implicit val mat = ActorMaterializer()

  val app = new ProxyApplication(proxyCfg)

  app.start()
}
