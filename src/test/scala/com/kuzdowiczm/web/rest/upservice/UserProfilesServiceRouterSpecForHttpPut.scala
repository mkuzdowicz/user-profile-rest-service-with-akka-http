package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.OK
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

    val endpoint = s"$usersPath"
    Put(endpoint, HttpEntity(`application/json`, jsonRequest)) ~> usrProfServiceCtrlRouter ~> check {
      status shouldEqual OK
      contentType shouldEqual `application/json`
      responseAs[ResponseResource].userProfile.firstname shouldEqual "newFirstname"
      responseAs[ResponseResource].userProfile.lastname shouldEqual userToUpdate.lastname
      responseAs[ResponseResource].userProfile.id shouldEqual userToUpdate.id
    }
  }

}

