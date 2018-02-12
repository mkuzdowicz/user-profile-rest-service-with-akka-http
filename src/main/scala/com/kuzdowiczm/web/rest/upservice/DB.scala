package com.kuzdowiczm.web.rest.upservice

object DB {

  val userProfiles: scala.collection.mutable.Map[String, UserProfile] =
    scala.collection.mutable.Map[String, UserProfile]()

  val organisations: scala.collection.mutable.Map[String, Organisation] =
    scala.collection.mutable.Map[String, Organisation]()


}
