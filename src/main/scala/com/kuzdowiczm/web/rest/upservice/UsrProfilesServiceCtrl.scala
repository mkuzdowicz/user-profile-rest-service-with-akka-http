package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.server.Directives
import com.kuzdowiczm.web.rest.upservice.repository.{OrganisationsRepo, OrganisationsRepoInMemoImpl, UserProfilesRepo, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.services.UserProfilesService
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val addressFormat = jsonFormat1(Address)
  implicit val organisationFormat = jsonFormat5(Organisation)
  implicit val usrProfileFormat = jsonFormat9(UserProfile)
}

class UsrProfilesServiceCtrl extends Directives with JsonSupport {

  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  private val usrProfilesService = UserProfilesService.apply

  val route =
    get {
      pathSingleSlash {
        complete {
          val res = usrProfilesService.findBy("sdfsdf")
          res
        }
      }
    }
}
