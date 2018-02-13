package com.kuzdowiczm.web.rest.upservice.services

import com.kuzdowiczm.web.rest.upservice.repository.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.{CreateUserReq, UserProfile}

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


