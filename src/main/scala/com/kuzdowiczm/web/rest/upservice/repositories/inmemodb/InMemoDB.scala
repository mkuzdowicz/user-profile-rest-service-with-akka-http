package com.kuzdowiczm.web.rest.upservice.repositories.inmemodb

import com.kuzdowiczm.web.rest.upservice.domain.{Organisation, UserProfile}

object InMemoDB {

  private val userIdToUserProfile: scala.collection.mutable.Map[String, UserProfile] =
    scala.collection.mutable.Map[String, UserProfile]()

  private val orgIdToOrganisation: scala.collection.mutable.Map[String, Organisation] =
    scala.collection.mutable.Map[String, Organisation]()

  def userProfiles() = {
    userIdToUserProfile
  }

  def organisations() = {
    orgIdToOrganisation
  }

}
