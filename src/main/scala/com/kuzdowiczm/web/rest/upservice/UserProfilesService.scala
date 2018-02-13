package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.db.{UserProfilesRepo, UserProfilesRepoInMemoImpl}

object UserProfilesService {

  private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl

  def add(createUserReq: CreateUserReq): String = {
    val org = OrganisationsService.findOneBy(createUserReq.orgName)
    usrProfilesRepo.createOneFrom(createUserReq, org)
  }

  def findBy(id: String): UserProfile = {
    usrProfilesRepo.findBy(id).orNull
  }

}
