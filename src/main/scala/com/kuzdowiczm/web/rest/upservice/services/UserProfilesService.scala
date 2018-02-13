package com.kuzdowiczm.web.rest.upservice.services

import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.{CreateOrUpdateUserReq, UserProfile}

object UserProfilesService {
  def apply(implicit usrProfilesRepo: UserProfilesRepo, orgsRepo: OrganisationsRepo): UserProfilesService = {
    new UserProfilesService()
  }
}

class UserProfilesService(implicit private val usrProfilesRepo: UserProfilesRepo, private val orgsRepo: OrganisationsRepo) {

  def createOrUpdate(createUserReq: CreateOrUpdateUserReq): Option[UserProfile] = {
    val org = orgsRepo.findOneBy(createUserReq.orgName).orNull
    usrProfilesRepo.createOrUpdateFrom(createUserReq, org)
  }

  def findOneBy(id: String): Option[UserProfile] = {
    usrProfilesRepo.findOneBy(id)
  }

  def deleteOneBy(id: String): Option[UserProfile] = {
    usrProfilesRepo.deleteOneBy(id)
  }
}


