package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.db.UserProfilesRepo

object UserProfilesService {
  def apply(implicit usrProfilesRepo: UserProfilesRepo): UserProfilesService = {
    new UserProfilesService()
  }
}

class UserProfilesService(implicit private val usrProfilesRepo: UserProfilesRepo) {
  def add(createUserReq: CreateUserReq): String = {
    val org = OrganisationsService.findOneBy(createUserReq.orgName)
    usrProfilesRepo.createOneFrom(createUserReq, org)
  }

  def findBy(id: String): UserProfile = {
    usrProfilesRepo.findBy(id).orNull
  }
}


