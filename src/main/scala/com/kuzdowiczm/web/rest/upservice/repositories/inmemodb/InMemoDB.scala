package com.kuzdowiczm.web.rest.upservice.repositories.inmemodb

import com.kuzdowiczm.web.rest.upservice.domain.{Organisation, UserProfile}

object InMemoDB {

  val userProfiles: scala.collection.mutable.Map[String, UserProfile] =
    scala.collection.concurrent.TrieMap[String, UserProfile]()

  val organisations: scala.collection.mutable.Map[String, Organisation] =
    scala.collection.concurrent.TrieMap[String, Organisation]()

}
