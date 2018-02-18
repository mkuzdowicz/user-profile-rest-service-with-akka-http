package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.{OK, BadRequest}
import akka.util.ByteString
import com.kuzdowiczm.web.rest.upservice.domain.ResponseResource
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper

class UserProfilesServiceRouterSpecForHttpPut extends UserProfilesServiceRouterSpecTrait {

  s"update user profile when http Put on $usersPath endpoint is hitted" in {

    val userToUpdate = DataInitHelper.initOneOrgAndOneUser

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
         |  "orgId": "${userToUpdate.organisation.get.id}",
         |  "address": { "postcode": "${userToUpdate.address.postcode}"}
         |}
        """.stripMargin)

    Put(usersPath, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
      status shouldEqual OK
      contentType shouldEqual `application/json`
      responseAs[ResponseResource].userProfile.firstname shouldEqual "newFirstname"
      responseAs[ResponseResource].userProfile.lastname shouldEqual userToUpdate.lastname
      responseAs[ResponseResource].userProfile.email shouldEqual userToUpdate.email
      responseAs[ResponseResource].userProfile.salutation shouldEqual userToUpdate.salutation
      responseAs[ResponseResource].userProfile.telephone shouldEqual userToUpdate.telephone
      responseAs[ResponseResource].userProfile.`type` shouldEqual userToUpdate.`type`
      responseAs[ResponseResource].userProfile.organisation.get.id shouldEqual userToUpdate.organisation.get.id
      responseAs[ResponseResource].userProfile.address.postcode shouldEqual userToUpdate.address.postcode
      responseAs[ResponseResource].userProfile.id shouldEqual userToUpdate.id
    }
  }

  s"return BadRequest when http Put on $usersPath/<non_existing_user_id>  endpoint is hitted" in {

    val jsonRequest = ByteString(
      s"""
         |{
         |  "id": "fake-123",
         |  "firstname": "newFirstname",
         |  "lastname": "non",
         |  "email": "email",
         |  "salutation": "Mr",
         |  "telephone": "123",
         |  "type": "barrister",
         |  "orgId": "123",
         |  "address": { "postcode": "EC2 99Y"}
         |}
        """.stripMargin)

    Put(usersPath, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
      status shouldEqual BadRequest
    }
  }

}

