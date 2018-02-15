package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.{`application/json`, `text/plain(UTF-8)`}
import akka.http.scaladsl.model.StatusCodes.{NoContent, NotFound, OK}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.kuzdowiczm.web.rest.upservice.InMemoDBTestUtils.clearInMemoDB
import com.kuzdowiczm.web.rest.upservice.domain.UserProfile
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper
import com.kuzdowiczm.web.rest.upservice.helpers.ErrorMessagesHelper.ifNoUserProfileFor
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.{InMemoDB, OrganisationsRepoInMemoImpl, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}

class UserProfilesServiceControllerSpec extends WordSpec with Matchers with BeforeAndAfterEach with ScalatestRouteTest with UsrProfilesServiceCtrlJsonSupport {

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

    s"return docs like response for /$mainEndpoint endpoint" in {
      Get(s"/$mainEndpoint/") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual OK
        contentType shouldEqual `application/json`
      }
    }

    s"return error message when http get on /$usersPath/<non_existing_user_id> endpoint is hitted" in {
      val nonExitingUserID = "fake-id-1456-ofp"
      val endpoint = s"$usersPath/$nonExitingUserID"
      Get(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NotFound
        contentType shouldEqual `text/plain(UTF-8)`
        responseAs[String] shouldEqual ifNoUserProfileFor(nonExitingUserID)
      }
    }

    s"return user profile when http get on /$usersPath/<existing_user_id> endpoint is hitted" in {
      val existingUser = DataInitHelper.initOneOrgAndOneUser
      val endpoint = s"$usersPath/${existingUser.id}"
      Get(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual OK
        contentType shouldEqual `application/json`
        responseAs[UserProfile] shouldEqual existingUser
      }
    }

    s"delete user when http delete methid on /$usersPath/<existing_user_id>  endpoint is hitted" in {
      val existingUser = DataInitHelper.initOneOrgAndOneUser
      val endpoint = s"$usersPath/${existingUser.id}"
      usrProfilesRepo.findAll().size shouldBe 1
      Delete(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NoContent
      }
      usrProfilesRepo.findAll().size shouldBe 0
    }
  }

}
