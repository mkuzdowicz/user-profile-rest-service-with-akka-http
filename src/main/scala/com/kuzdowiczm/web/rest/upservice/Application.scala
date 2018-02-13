package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.repository.{OrganisationsRepo, OrganisationsRepoInMemoImpl, UserProfilesRepo, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.services.{OrganisationsService, UserProfilesService}
import akka.http.scaladsl.server.Directives


object Application extends App with Directives {

  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  private val usrProfilesService = UserProfilesService.apply
  private val orgsService = OrganisationsService.apply

  DataInitialiser.init(usrProfilesService, orgsService)

}
