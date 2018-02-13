package com.kuzdowiczm.web.rest.upservice.repository

import java.util.UUID

import com.kuzdowiczm.web.rest.upservice._

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

}
