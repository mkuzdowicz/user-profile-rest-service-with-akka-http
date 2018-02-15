package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.{`application/json`, `text/plain(UTF-8)`}
import akka.http.scaladsl.model.StatusCodes.{Created, NoContent, NotFound, OK}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.{OrganisationsRepoInMemoImpl, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.helpers.ErrorMessagesHelper.{ifNewUserCreatedWith, ifNoUserProfileFor, ifUserUserUpdatedWith}
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import com.kuzdowiczm.web.rest.upservice.InMemoDBTestUtils.clearInMemoDB
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.InMemoDB

class UsrProfilesServiceCtrlSpec extends WordSpec with Matchers with BeforeAndAfterEach with ScalatestRouteTest {

  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  private val usrProfServiceCtrl = new UsrProfilesServiceCtrl()
  private val usrProfServiceCtrlRouter = usrProfServiceCtrl.route

  val mainEndpoint = usrProfServiceCtrl.mainEndpoint
  val usersPath = s"/$mainEndpoint/${usrProfServiceCtrl.usersEndpoint}"

  override def beforeEach(): Unit = {
    clearInMemoDB(InMemoDB)
  }

  "user profile service" should {

    s"return error message user when http get on /$usersPath/<non_existing_user_id> endpoint is hitted" in {
      val nonExitingUserID = "fake-id-1456-ofp"
      Get(s"/$usersPath/$nonExitingUserID") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NotFound
        contentType shouldEqual `text/plain(UTF-8)`
        responseAs[String] shouldEqual ifNoUserProfileFor(nonExitingUserID)
      }
    }

    s"delete user when http delete methid on /$usersPath/<existing_user_id>  endpoint is hitted" in {
      val existingUserId = DataInitHelper.initOneOrgAndOneUser
      usrProfilesRepo.findAll().size shouldBe 1
      Delete(s"/$usersPath/$existingUserId") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NoContent
      }
      usrProfilesRepo.findAll().size shouldBe 0
    }

    s"return docs like response for /$mainEndpoint endpoint" in {
      Get(s"/$mainEndpoint/") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual OK
        contentType shouldEqual `application/json`
      }
    }
  }

}
