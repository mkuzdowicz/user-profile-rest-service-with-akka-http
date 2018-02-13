package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.db.{OrganisationsRepo, UserProfilesRepo}

object UserProfilesService {
  def apply(implicit usrProfilesRepo: UserProfilesRepo, orgsRepo: OrganisationsRepo): UserProfilesService = {
    new UserProfilesService()
  }
}

class UserProfilesService(implicit private val usrProfilesRepo: UserProfilesRepo, private val orgsRepo: OrganisationsRepo) {
  def add(createUserReq: CreateUserReq): String = {
    val org = orgsRepo.findOneBy(createUserReq.orgName).orNull
    usrProfilesRepo.createOneFrom(createUserReq, org)
  }

  def findBy(id: String): UserProfile = {
    usrProfilesRepo.findBy(id).orNull
  }
}


