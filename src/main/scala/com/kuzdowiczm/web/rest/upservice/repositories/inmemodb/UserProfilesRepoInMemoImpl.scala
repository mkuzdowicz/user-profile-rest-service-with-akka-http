package com.kuzdowiczm.web.rest.upservice.repositories.inmemodb

import java.util.UUID

import com.kuzdowiczm.web.rest.upservice._
import com.kuzdowiczm.web.rest.upservice.repositories.UserProfilesRepo

object UserProfilesRepoInMemoImpl extends UserProfilesRepo {

  def createOneFrom(createUserReq: CreateUserReq, org: Organisation): String = {

    val uuid = UUID.randomUUID().toString
    InMemoDB.userProfiles += uuid -> UserProfile(
      id = uuid,
      firstname = createUserReq.firstname,
      lastname = createUserReq.lastname,
      email = createUserReq.email,
      salutation = createUserReq.salutation,
      telephone = createUserReq.telephone,
      `type` = createUserReq.`type`,
      organisation = org,
      address = createUserReq.address
    )
    uuid
  }

  def findBy(id: String): Option[UserProfile] = {
    InMemoDB.userProfiles.get(id)
  }

  def deleteBy(id: String): Boolean = {
    InMemoDB.userProfiles.remove(id).nonEmpty
  }

}
