package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.Created
import akka.util.ByteString
import com.kuzdowiczm.web.rest.upservice.domain.{ResponseResource, UserProfile}
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper

class UserProfilesServiceRouterSpecForHttpPost extends UserProfilesServiceRouterSpecTrait {

  "user profile service" should {

    s"create user profile when http POST on $usersPath endpoint is hitted with jsonRequest without id" in {

      val orgId = DataInitHelper.initOneOrg

      val jsonRequest = ByteString(
        s"""
           |{
           |  "firstname": "testFirstname",
           |  "lastname": "testLastname",
           |  "email": "test@gmail.com",
           |  "salutation": "Mr",
           |  "telephone": "+11 11 111111",
           |  "type": "barrister",
           |  "orgId": "$orgId",
           |  "address": { "postcode": "E00 00P" }
           |}
        """.stripMargin)

      val endpoint = s"$usersPath"
      Post(endpoint, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual Created
        contentType shouldEqual `application/json`
        responseAs[ResponseResource].userProfile.firstname shouldEqual "testFirstname"
        responseAs[ResponseResource].userProfile.lastname shouldEqual "testLastname"
        responseAs[ResponseResource].userProfile.id.nonEmpty shouldBe true
      }
    }

    s"create user profile when http POST on $usersPath endpoint is hitted with jsonRequest with empty id" in {

      val orgId = DataInitHelper.initOneOrg

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
           |  "orgId": "$orgId",
           |  "address": { "postcode": "E00 00P" }
           |}
        """.stripMargin)

      val endpoint = s"$usersPath"
      Post(endpoint, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
        status shouldEqual Created
        contentType shouldEqual `application/json`
        responseAs[ResponseResource].userProfile.firstname shouldEqual "testFirstname"
        responseAs[ResponseResource].userProfile.lastname shouldEqual "testLastname"
        responseAs[ResponseResource].userProfile.id.nonEmpty shouldBe true
      }
    }

  }

  s"create user profile when http POST on $usersPath endpoint is hitted with jsonRequest with empty org id" in {

    val jsonRequest = ByteString(
      s"""
         |{
         |  "firstname": "testFirstname",
         |  "lastname": "testLastname",
         |  "email": "test@gmail.com",
         |  "salutation": "Mr",
         |  "telephone": "+11 11 111111",
         |  "type": "barrister",
         |  "orgId": "",
         |  "address": { "postcode": "E00 00P" }
         |}
        """.stripMargin)

    val endpoint = s"$usersPath"
    Post(endpoint, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
      status shouldEqual Created
      contentType shouldEqual `application/json`
      responseAs[ResponseResource].userProfile.firstname shouldEqual "testFirstname"
      responseAs[ResponseResource].userProfile.lastname shouldEqual "testLastname"
      responseAs[ResponseResource].userProfile.id.nonEmpty shouldBe true
    }
  }


}
