package com.kuzdowiczm.web.rest.upservice

object UserProfilesService {

  def add(createUserReq: CreateUserReq): String = {
    UserProfilesRepo.addOne(createUserReq)
  }

  def findBy(id: String): UserProfile = {
    UserProfilesRepo.findBy(id).orNull
  }

}
