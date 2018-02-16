package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives
import com.kuzdowiczm.web.rest.upservice.domain._
import com.kuzdowiczm.web.rest.upservice.helpers.DocsResponse.SERVICE_LINKS_MAP
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.services.UserProfilesService
import com.typesafe.config.ConfigFactory
import spray.json.DefaultJsonProtocol

trait UserProfilesServiceControllerJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val addressFormat = jsonFormat1(Address)
  implicit val organisationFormat = jsonFormat5(Organisation)
  implicit val usrProfileFormat = jsonFormat9(UserProfile)
  implicit val createUserReq = jsonFormat9(CreateOrUpdateUserReq)
  implicit val responseResource = jsonFormat2(ResponseResource)
}

object UserProfilesServiceController {
  def apply(implicit usrProfilesRepo: UserProfilesRepo, orgsRepo: OrganisationsRepo): UserProfilesServiceController = {
    new UserProfilesServiceController()
  }
}

class UserProfilesServiceController(implicit private val usrProfilesRepo: UserProfilesRepo, private val orgsRepo: OrganisationsRepo)
  extends Directives with UserProfilesServiceControllerJsonSupport {

  private val cfg = ConfigFactory.load()
  val mainEndpoint = cfg.getString("app.service_main_endpoint")
  val usersEndpoint = cfg.getString("app.users_endpoint")

  private val usrProfilesService = UserProfilesService.apply

  val route =
    pathPrefix(mainEndpoint) {
      pathSingleSlash {
        get {
          complete(OK, SERVICE_LINKS_MAP)
        }
      } ~
        pathPrefix(usersEndpoint) {
          path(Segment) { id =>
            get {
              usrProfilesService.findOneBy(id) match {
                case Some(userProfile) => complete(OK, userProfile)
                case None => complete(NotFound)
              }
            } ~ delete {
              usrProfilesService.deleteOneBy(id) match {
                case Some(_) => complete(NoContent)
                case None => complete(NotFound)
              }
            }
          } ~
            post {
              entity(as[CreateOrUpdateUserReq]) { createUsrReq =>
                usrProfilesService.createOrUpdate(createUsrReq) match {
                  case Some(newUsr) => complete(Created, newUsr)
                  case None => complete(Conflict)
                }
              }
            } ~
            put {
              entity(as[CreateOrUpdateUserReq]) { updateUsrReq =>
                usrProfilesService.createOrUpdate(updateUsrReq) match {
                  case Some(updatedUsr) => complete(OK, updatedUsr)
                  case None => complete(NotFound)
                }
              }
            }
        }
    }
}
