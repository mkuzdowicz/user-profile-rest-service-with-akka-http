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

  private val usrProfilesService = UserProfilesService.apply

  val route = pathPrefix("users") {
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
