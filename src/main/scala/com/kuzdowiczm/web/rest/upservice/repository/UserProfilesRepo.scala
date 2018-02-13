package com.kuzdowiczm.web.rest.upservice.repository

import com.kuzdowiczm.web.rest.upservice.{CreateUserReq, Organisation, UserProfile}

trait UserProfilesRepo {

  def createOneFrom(createUserReq: CreateUserReq, org: Organisation): String

  def findBy(id: String): Option[UserProfile]

}