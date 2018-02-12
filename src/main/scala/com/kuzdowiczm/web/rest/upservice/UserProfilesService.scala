package com.kuzdowiczm.web.rest.upservice

import java.util.UUID

object UserProfilesService {

  def add(createUserReq: CreateUserReq): String = {
    val uuid = UUID.randomUUID().toString
    DB.userProfiles += uuid -> UserProfile(
      id = uuid,
      firstname = createUserReq.firstname,
      lastname = createUserReq.lastname,
      email = createUserReq.email,
      salutation = createUserReq.salutation,
      telephone = createUserReq.telephone,
      `type` = createUserReq.`type`
    )
    uuid
  }

  def findBy(id: String): Option[UserProfile] = {
    DB.userProfiles.get(id)
  }

}
