package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes.{Created, NoContent, NotFound, OK}
import akka.http.scaladsl.server.Directives
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.services.UserProfilesService
import com.typesafe.config.ConfigFactory
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val addressFormat = jsonFormat1(Address)
  implicit val organisationFormat = jsonFormat5(Organisation)
  implicit val usrProfileFormat = jsonFormat9(UserProfile)
  implicit val createUserReq = jsonFormat9(CreateOrUpdateUserReq)
}

class UsrProfilesServiceCtrl(implicit private val usrProfilesRepo: UserProfilesRepo, private val orgsRepo: OrganisationsRepo)
  extends Directives with JsonSupport {

  private val serviceLinksDocs = Map[String, String](
    "HTTP get: /users/<id>" -> "get single user",
    "HTTP delete: /users/<id>" -> "delete single user",
    "HTTP put: /users" -> "update single user (requires json payload)",
    "HTTP post: /users" -> "create single user (requires json payload)"
  )

  private val cfg = ConfigFactory.load()
  private val mainEndpoint = cfg.getString("app.service_main_endpoint")

  private val usrProfilesService = UserProfilesService.apply

  val route =
    pathSingleSlash {
      get {
        complete(OK, serviceLinksDocs)
      }
    } ~
      pathPrefix(mainEndpoint) {
        path(Segment) { id =>
          get {
            usrProfilesService.findBy(id) match {
              case Some(userProfile) => complete(OK, userProfile)
              case None => complete(NotFound, s"there is no user profile with id: $id")
            }
          } ~ delete {
            usrProfilesService.deleteBy(id) match {
              case Some(_) => complete(NoContent)
              case None => complete(NotFound, s"no user founded for id: $id")
            }
          }
        } ~
          post {
            entity(as[CreateOrUpdateUserReq]) { createUsrReq =>
              val newUsr = usrProfilesService.createOrUpdate(createUsrReq).get
              complete(Created, s"new user created with id: ${newUsr.id}")
            }
          } ~
          put {
            entity(as[CreateOrUpdateUserReq]) { updateUsrReq =>
              usrProfilesService.createOrUpdate(updateUsrReq) match {
                case Some(usr) => complete(OK, s"user with id: ${usr.id} updated")
                case None => complete(NotFound, s"there is no user profile with id: ${updateUsrReq.id}")
              }
            }
          }
      }

}
