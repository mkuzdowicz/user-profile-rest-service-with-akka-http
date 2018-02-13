package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.{OrganisationsRepoInMemoImpl, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import org.scalatest.{Matchers, WordSpec}

class UsrProfilesServiceCtrlSpec extends WordSpec with Matchers with ScalatestRouteTest {

  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  val existingUserId = DataInitialiser.init

  private val usrProfServiceCtrlRouter = new UsrProfilesServiceCtrl().route

  "user profile service" should {

    "return message for non existed user id" in {

      val nonExitingUserID = "fake-id-1456-ofp"

      Get(s"/users/$nonExitingUserID") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual StatusCodes.NotFound
        contentType shouldEqual ContentTypes.`text/plain(UTF-8)`
        responseAs[String] shouldEqual s"there is no user profile with id: $nonExitingUserID"
      }
    }
  }

}
