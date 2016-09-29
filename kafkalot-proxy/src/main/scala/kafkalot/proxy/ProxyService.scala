package kafkalot.proxy

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._

import ch.megard.akka.http.cors.CorsDirectives._

case class User(name: String, age: Int)
case class EnhancedUser(name: String, age: Int, uuid: UUID)

trait ProxyService {
  import de.heikoseeberger.akkahttpcirce.CirceSupport._
  import io.circe.generic.auto._

  // format: OFF
  private def userApi =
    pathEndOrSingleSlash {
      get { complete(User("Tommy", 42)) } ~
      post {
        entity(as[User]) { user =>
          complete(EnhancedUser(user.name, user.age, UUID.randomUUID()))
        }
      }
    }
  // format: ON

  // format: OFF
  /** serving static resources */
  private def assets =
  pathPrefix("") {
    getFromResourceDirectory("dist") ~
    getFromResource("dist/index.html") // fallback
  }
  // format: ON

  // format: OFF
  private def api =
    pathPrefix("api") {
      pathPrefix("v1") {
        pathPrefix("user") { userApi }
      } ~
      complete(NotFound)
    }
  // format: ON

  def createHttpHandler() = {
    // format: OFF
    logRequest("API") { cors() { api } } ~
    logRequest("ASSET") { assets }
    // format: ON
  }
}

