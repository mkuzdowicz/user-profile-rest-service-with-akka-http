package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.helpers.DataInitHelper
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.{OrganisationsRepoInMemoImpl, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.typesafe.config.ConfigFactory


object Application extends App {

  private val cfg = ConfigFactory.load()

  // user-profiles-service dependencies
  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  DataInitHelper.initOneOrgAndOneUser

  val usrProfServiceCtrlRouter = new UsrProfilesServiceCtrl().route

  val host = cfg.getString("app.host")
  val port = cfg.getInt("app.port")

  Server.startServer(usrProfServiceCtrlRouter, host, port)

}
