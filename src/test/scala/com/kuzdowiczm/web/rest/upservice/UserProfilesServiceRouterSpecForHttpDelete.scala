package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.StatusCodes.{NoContent, BadRequest}
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper

class UserProfilesServiceRouterSpecForHttpDelete extends UserProfilesServiceRouterSpecTrait {

  "user profile service" should {

    s"delete user when http DELETE on $usersPath/<existing_user_id>  endpoint is hitted" in {
      val existingUser = DataInitHelper.initOneOrgAndOneUser
      val endpoint = s"$usersPath/${existingUser.id}"
      usrProfilesRepo.findOneBy(existingUser.id).isDefined shouldBe true
      Delete(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NoContent
      }
      usrProfilesRepo.findOneBy(existingUser.id).isEmpty shouldBe true
    }

    s"return BadRequest when http Delete on $usersPath/<non_existing_user_id>  endpoint is hitted" in {
      val nonExistingUser = "fake-123"
      val endpoint = s"$usersPath/$nonExistingUser"
      Delete(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual BadRequest
      }
    }
  }

}
