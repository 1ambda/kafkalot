package kafkalot.proxy

import java.util.UUID

import scala.concurrent.{ ExecutionContext }
import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer

import ch.megard.akka.http.cors.CorsDirectives._

case class User(name: String, age: Int)
case class EnhancedUser(name: String, age: Int, uuid: UUID)

trait ProxyService {
  import de.heikoseeberger.akkahttpcirce.CirceSupport._
  import io.circe.generic.auto._

  def createUserApi() = {
    // format: OFF
    pathEndOrSingleSlash {
      get {
        complete(User("Tommy", 42))
      } ~
        post {
          entity(as[User]) { user =>
            complete(EnhancedUser(user.name, user.age, UUID.randomUUID()))
          }
        }
    }
    // format: ON
  }

  def createHttpHandler(implicit
    system: ActorSystem,
    ec: ExecutionContext,
    mat: Materializer) = {

    // format: OFF
    cors() {
      pathPrefix("api") {
        pathPrefix("v1") {
          pathPrefix("user") {
            createUserApi()
          }
        } ~ complete(NotFound)
      }
    } ~
      pathPrefix("") {
        /** serving static resources */
        getFromResourceDirectory("dist") ~
          getFromResource("dist/index.html") // fallback
      }
    // format: ON
  }
}

