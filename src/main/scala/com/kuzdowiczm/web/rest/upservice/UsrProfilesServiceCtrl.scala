package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.services.UserProfilesService
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val addressFormat = jsonFormat1(Address)
  implicit val organisationFormat = jsonFormat5(Organisation)
  implicit val usrProfileFormat = jsonFormat9(UserProfile)
  implicit val createUserReq = jsonFormat8(CreateUserReq)
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
              val result = usrProfilesService.findBy(id)
              result
            }
          } ~ delete {
            complete {
              val result = usrProfilesService.deleteBy(id)
              val res = if (result) s"deleted user with id: $id" else s"no user found for id: $id"
              res
            }
          }
        } ~
          post {
            entity(as[CreateUserReq]) { createUsrReq =>
              val nUsrId = usrProfilesService.add(createUsrReq)
              complete(s"new user created with id: $nUsrId")
            }
          } ~
          put {
            entity(as[CreateUserReq]) { createUsrReq =>
              val nUsrId = usrProfilesService.add(createUsrReq)
              complete(s"user updated")
            }
          }
      }

}
