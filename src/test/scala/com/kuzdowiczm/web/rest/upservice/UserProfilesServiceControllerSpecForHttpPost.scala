package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.Created
import akka.util.ByteString
import com.kuzdowiczm.web.rest.upservice.domain.UserProfile
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper

class UserProfilesServiceControllerSpecForHttpPost extends UserProfilesServiceControllerSpecTrait {

  "user profile service" should {

    s"create user profile when http POST on $usersPath endpoint is hitted with jsonRequest without id" in {

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

    s"create user profile when http POST on $usersPath endpoint is hitted with jsonRequest with empty id" in {

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

  }
}
