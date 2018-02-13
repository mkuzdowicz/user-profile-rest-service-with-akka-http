package com.kuzdowiczm.web.rest.upservice.repositories

import com.kuzdowiczm.web.rest.upservice.{CreateOrUpdateUserReq, Organisation, UserProfile}

trait UserProfilesRepo {

  def createOrUpdateFrom(createUserReq: CreateOrUpdateUserReq, org: Organisation): Option[UserProfile]

  def findBy(id: String): Option[UserProfile]

  def deleteBy(id: String): Option[UserProfile]

}
