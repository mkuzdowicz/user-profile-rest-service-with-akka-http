package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.{Conflict, Created}
import akka.util.ByteString
import com.kuzdowiczm.web.rest.upservice.domain.ResponseResource
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper

class UserProfilesServiceRouterSpecForHttpPost extends UserProfilesServiceRouterSpecTrait {

  "user profile service" should {

    s"create user profile when http POST on $usersPath endpoint is hitted with correct json request payload" in {

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

    s"return BadRequest Status when http POST on $usersPath endpoint is hitted with payload with specified id field" in {

      val orgId = DataInitHelper.initOneOrg

      val jsonRequest = ByteString(
        s"""
           |{
           |  "id": "02b1af45-dd33-4207-bde8-5c68a-dfg-457",
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
        status shouldEqual Conflict
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
