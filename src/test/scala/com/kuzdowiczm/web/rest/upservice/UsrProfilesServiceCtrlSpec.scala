package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.StatusCodes.{Created, NoContent, NotFound, OK}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.{OrganisationsRepoInMemoImpl, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.ErrorMessagesHelper.{ifNewUserCreatedWith, ifNoUserProfileFor, ifUserUserUpdatedWith}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import com.kuzdowiczm.web.rest.upservice.InMemoDBTestUtils.clearInMemoDB
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.InMemoDB

class UsrProfilesServiceCtrlSpec extends WordSpec with Matchers with BeforeAndAfterEach with ScalatestRouteTest {

  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  private val cfg = ConfigFactory.load()
  private val mainEndpoint = cfg.getString("app.service_main_endpoint")

  private val usrProfServiceCtrlRouter = new UsrProfilesServiceCtrl().route

  override def beforeEach(): Unit = {
    clearInMemoDB(InMemoDB)
  }

  "user profile service" should {

    "return message for non existed user id" in {
      val nonExitingUserID = "fake-id-1456-ofp"
      Get(s"/$mainEndpoint/$nonExitingUserID") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NotFound
        contentType shouldEqual ContentTypes.`text/plain(UTF-8)`
        responseAs[String] shouldEqual ifNoUserProfileFor(nonExitingUserID)
      }
    }

    "return message for delete existed user id" in {
      val existingUserId = DataInitHelper.initOneOrgAndOneUser
      usrProfilesRepo.findAll().size shouldBe 1
      Delete(s"/$mainEndpoint/$existingUserId") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NoContent
      }
      usrProfilesRepo.findAll().size shouldBe 0
    }

//    "return docs like response for single slash response" in {
//      Get(s"/$mainEndpoint") ~> usrProfServiceCtrlRouter ~> check {
//        status shouldEqual OK
////        contentType shouldEqual ContentTypes.`application/json`
////        responseAs[String] shouldEqual ifNoUserProfileFor(nonExitingUserID)
//      }
//    }
  }

}
