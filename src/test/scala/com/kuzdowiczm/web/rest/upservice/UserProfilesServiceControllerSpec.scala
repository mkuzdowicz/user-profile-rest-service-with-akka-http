package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.{`application/json`, `text/plain(UTF-8)`}
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.{Created, NoContent, NotFound, OK}
import akka.util.ByteString
import com.kuzdowiczm.web.rest.upservice.domain.UserProfile
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper
import com.kuzdowiczm.web.rest.upservice.helpers.ResponseMessagesHelper.ifNoUserProfileFor

class UserProfilesServiceControllerSpec extends UserProfilesServiceControllerSpecTrait {

  "user profile service" should {

    s"return docs like response when http GET on /$mainEndpoint/ endpoint" in {
      Get(s"/$mainEndpoint/") ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual OK
        contentType shouldEqual `application/json`
      }
    }

    s"return error message when http GET on /$usersPath/<non_existing_user_id> endpoint is hitted" in {
      val nonExitingUserID = "fake-id-1456-ofp"
      val endpoint = s"$usersPath/$nonExitingUserID"
      Get(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NotFound
        contentType shouldEqual `text/plain(UTF-8)`
        responseAs[String] shouldEqual ifNoUserProfileFor(nonExitingUserID)
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

    s"create user profile when http POST on /$usersPath endpoint is hitted with jsonRequest without id" in {

      val orgName = DataInitHelper.initOneOrg

      val jsonRequest = ByteString(
        s"""
           |{
           |  "firstname": "testFirstname",
           |  "lastname": "testLastname",
           |  "email": "test@gmail.com",
           |  "salutation": "Mr",
           |  "telephone": "+11 11 111111",
           |  "type": "barrister",
           |  "orgName": "$orgName",
           |  "address": { "postcode": "E00 00P" }
           |}
        """.stripMargin)

      val endpoint = s"$usersPath"
      Post(endpoint, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual Created
        contentType shouldEqual `application/json`
        responseAs[UserProfile].firstname shouldEqual "testFirstname"
        responseAs[UserProfile].lastname shouldEqual "testLastname"
        responseAs[UserProfile].id.nonEmpty shouldBe true
      }
    }

    s"create user profile when http POST on /$usersPath endpoint is hitted with jsonRequest with empty id" in {

      val orgName = DataInitHelper.initOneOrg

      val jsonRequest = ByteString(
        s"""
           |{
           |  "id": "",
           |  "firstname": "testFirstname",
           |  "lastname": "testLastname",
           |  "email": "test@gmail.com",
           |  "salutation": "Mr",
           |  "telephone": "+11 11 111111",
           |  "type": "barrister",
           |  "orgName": "$orgName",
           |  "address": { "postcode": "E00 00P" }
           |}
        """.stripMargin)

      val endpoint = s"$usersPath"
      Post(endpoint, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual Created
        contentType shouldEqual `application/json`
        responseAs[UserProfile].firstname shouldEqual "testFirstname"
        responseAs[UserProfile].lastname shouldEqual "testLastname"
        responseAs[UserProfile].id.nonEmpty shouldBe true
      }
    }

    s"delete user when http DELETE on /$usersPath/<existing_user_id>  endpoint is hitted" in {
      val existingUser = DataInitHelper.initOneOrgAndOneUser
      val endpoint = s"$usersPath/${existingUser.id}"
      usrProfilesRepo.findAll().size shouldBe 1
      Delete(endpoint) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual NoContent
      }
      usrProfilesRepo.findAll().size shouldBe 0
    }
  }

  s"update user profile when http POST on /$usersPath endpoint is hitted" in {

    val userToUpdate = DataInitHelper.initOneOrgAndOneUser
    val orgName = userToUpdate.organisation.name

    val jsonRequest = ByteString(
      s"""
         |{
         |  "id": "${userToUpdate.id}",
         |  "firstname": "newFirstname",
         |  "lastname": "${userToUpdate.lastname}",
         |  "email": "${userToUpdate.email}",
         |  "salutation": "${userToUpdate.salutation}",
         |  "telephone": "${userToUpdate.telephone}",
         |  "type": "${userToUpdate.`type`}",
         |  "orgName": "$orgName",
         |  "address": { "postcode": "${userToUpdate.address.postcode}"}
         |}
        """.stripMargin)

    val endpoint = s"$usersPath"
    Put(endpoint, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
      status shouldEqual OK
      contentType shouldEqual `application/json`
      responseAs[UserProfile].firstname shouldEqual "newFirstname"
      responseAs[UserProfile].lastname shouldEqual userToUpdate.lastname
      responseAs[UserProfile].id shouldEqual userToUpdate.id
    }
  }


}
