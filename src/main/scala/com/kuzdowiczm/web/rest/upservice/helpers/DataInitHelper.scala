package com.kuzdowiczm.web.rest.upservice.helpers

import com.kuzdowiczm.web.rest.upservice.domain.{Address, CreateOrUpdateUserReq, CreateOrgReq, UserProfile}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.services.{OrganisationsService, UserProfilesService}
import org.slf4j.LoggerFactory

object DataInitHelper {

  private val log = LoggerFactory.getLogger(getClass)

  def initOneOrgAndOneUser(implicit usrProfilesRepo: UserProfilesRepo, orgsRepo: OrganisationsRepo): UserProfile = {
    log.info(s"creating initial data")

    val usrProfilesService = UserProfilesService.apply

    initOneOrg

    val usr1 = usrProfilesService.createOrUpdate(
      CreateOrUpdateUserReq(
        firstname = "Martin",
        lastname = "Kuzdowicz",
        email = "test@gmail.com",
        salutation = "Mr",
        telephone = "+44 1111 000000",
        `type` = "barrister",
        orgName = "Advice UK",
        address = Address(postcode = "E00 00P")
      )
    ).get

    log.info(s"usr1 => ${usr1.id}")
    usr1
  }

  def initOneOrg(implicit orgsRepo: OrganisationsRepo): String = {
    val orgsService = OrganisationsService.apply

    orgsService.create(CreateOrgReq(
      name = "Advice UK",
      email = "test@email",
      `type` = "ADVICE_SERVICE",
      address = Address(postcode = "EC2 67")
    )).get.name
  }

}
