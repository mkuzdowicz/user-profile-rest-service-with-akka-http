package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import com.kuzdowiczm.web.rest.upservice.repository.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.services.UserProfilesService
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val addressFormat = jsonFormat1(Address)
  implicit val organisationFormat = jsonFormat5(Organisation)
  implicit val usrProfileFormat = jsonFormat9(UserProfile)
}

class UsrProfilesServiceCtrl(implicit private val usrProfilesRepo: UserProfilesRepo, private val orgsRepo: OrganisationsRepo)
  extends Directives with JsonSupport {

  val serviceLinksRes = Map[String, String](
    "HTTP get: /users/<id>" -> "get single user",
    "HTTP delete: /users/<id>" -> "delete single user",
    "HTTP put: /users" -> "update single user (requires json payload)",
    "HTTP post: /users" -> "create single user (requires json payload)"
  )

  private val usrProfilesService = UserProfilesService.apply

  val route =
    pathSingleSlash {
      get {
        complete(serviceLinksRes)
      }
    } ~
      pathPrefix("users") {
        path(Segment) { id =>
          get {
            complete {
              val res = usrProfilesService.findBy(id)
              res
            }
          }
        }
      }

}
