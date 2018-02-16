package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.OK
import akka.util.ByteString
import com.kuzdowiczm.web.rest.upservice.domain.UserProfile
import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper

class UserProfilesServiceControllerSpecForHttpPut extends UserProfilesServiceControllerSpecTrait {

  s"update user profile when http Put on $usersPath endpoint is hitted" in {

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

