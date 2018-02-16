package com.kuzdowiczm.web.rest.upservice.repositories

import com.kuzdowiczm.web.rest.upservice.domain.{CreateOrUpdateUserReq, Organisation, UserProfile}

trait UserProfilesRepo {

  def createOrUpdateFrom(createUserReq: CreateOrUpdateUserReq, org: Organisation): Option[UserProfile]

  def findOneBy(id: String): Option[UserProfile]

  def deleteOneBy(id: String): Option[UserProfile]

}
