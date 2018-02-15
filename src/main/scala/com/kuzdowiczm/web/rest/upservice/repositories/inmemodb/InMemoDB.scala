package com.kuzdowiczm.web.rest.upservice.repositories.inmemodb

import com.kuzdowiczm.web.rest.upservice.{Organisation, UserProfile}

object InMemoDB {

  val userProfiles: scala.collection.mutable.Map[String, UserProfile] =
    scala.collection.mutable.Map[String, UserProfile]()

  val organisations: scala.collection.mutable.Map[String, Organisation] =
    scala.collection.mutable.Map[String, Organisation]()

}
