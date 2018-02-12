package com.kuzdowiczm.web.rest.upservice

import java.util.UUID

object UserProfilesRepo {

  def addOne(createUserReq: CreateUserReq): String = {

    val org = OrganisationsRepo.findOneBy(createUserReq.orgName).orNull

    val uuid = UUID.randomUUID().toString
    DB.userProfiles += uuid -> UserProfile(
      id = uuid,
      firstname = createUserReq.firstname,
      lastname = createUserReq.lastname,
      email = createUserReq.email,
      salutation = createUserReq.salutation,
      telephone = createUserReq.telephone,
      `type` = createUserReq.`type`,
      organisation = org
    )
    uuid
  }

  def findBy(id: String): Option[UserProfile] = {
    DB.userProfiles.get(id)
  }

}
