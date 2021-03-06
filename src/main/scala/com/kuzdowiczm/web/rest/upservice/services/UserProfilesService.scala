package com.kuzdowiczm.web.rest.upservice.services

import com.kuzdowiczm.web.rest.upservice.domain.{CreateOrUpdateUserReq, UserProfile}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}

object UserProfilesService {
  def apply(implicit usrProfilesRepo: UserProfilesRepo, orgsRepo: OrganisationsRepo): UserProfilesService = {
    new UserProfilesService()
  }
}

class UserProfilesService(implicit private val usrProfilesRepo: UserProfilesRepo, private val orgsRepo: OrganisationsRepo) {

  def createNewFrom(createUserReq: CreateOrUpdateUserReq): Option[UserProfile] = {
    val org = orgsRepo.findOneBy(createUserReq.orgId)
    createUserReq.id match {
      case None => usrProfilesRepo.createOrUpdateFrom(createUserReq, org)
      case Some(_) => None
    }
  }

  def update(createUserReq: CreateOrUpdateUserReq): Option[UserProfile] = {
    val org = orgsRepo.findOneBy(createUserReq.orgId)
    usrProfilesRepo.createOrUpdateFrom(createUserReq, org)
  }

  def findOneBy(id: String): Option[UserProfile] = {
    usrProfilesRepo.findOneBy(id)
  }

  def deleteOneBy(id: String): Option[UserProfile] = {
    usrProfilesRepo.deleteOneBy(id)
  }
}


