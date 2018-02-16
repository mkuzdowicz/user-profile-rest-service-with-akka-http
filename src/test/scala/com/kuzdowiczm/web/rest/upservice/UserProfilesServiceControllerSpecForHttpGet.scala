package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.{`application/json`, `text/plain(UTF-8)`}
import akka.http.scaladsl.model.StatusCodes.{NotFound, OK}
import com.kuzdowiczm.web.rest.upservice.domain.{ResponseResource, UserProfile}
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper
import com.kuzdowiczm.web.rest.upservice.helpers.ResponseMessagesHelper.ifNoUserProfileFor


class UserProfilesServiceControllerSpecForHttpGet extends UserProfilesServiceControllerSpecTrait {

  "user profile service" should {

    s"return docs like response when http GET on /$mainEndpoint/ endpoint" in {
      Get(s"/$mainEndpoint/") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual OK
        contentType shouldEqual `application/json`
      }
    }

    s"return error message when http GET on /$usersPath/<non_existing_user_id> endpoint is hitted" in {
      val nonExitingUserID = "fake-id-123-ofp"
      val endpoint = s"$usersPath/$nonExitingUserID"
      Get(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NotFound
        contentType shouldEqual `application/json`
        responseAs[ResponseResource].msg.get shouldEqual ifNoUserProfileFor(nonExitingUserID)
      }
    }

    s"return user profile when http GET on /$usersPath/<existing_user_id> endpoint is hitted" in {
      val existingUser = DataInitHelper.initOneOrgAndOneUser
      val endpoint = s"$usersPath/${existingUser.id}"
      Get(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual OK
        contentType shouldEqual `application/json`
        responseAs[UserProfile] shouldEqual existingUser
      }
    }
  }

}
