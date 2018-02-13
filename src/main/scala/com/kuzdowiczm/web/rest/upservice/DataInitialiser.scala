package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.kuzdowiczm.web.rest.upservice.services.{OrganisationsService, UserProfilesService}
import org.slf4j.LoggerFactory

object DataInitialiser {

  private val log = LoggerFactory.getLogger(getClass)

  def init(implicit usrProfilesRepo: UserProfilesRepo, orgsRepo: OrganisationsRepo) = {
    log.info(s"creating initial data")

    val usrProfilesService = UserProfilesService.apply
    val orgsService = OrganisationsService.apply

    orgsService.add(CreateOrgReq(
      name = "Advice UK",
      email = "test@email",
      `type` = "ADVICE_SERVICE",
      address = Address(postcode = "EC2 67")
    ))

    val usr1UUID = usrProfilesService.add(
      CreateUserReq(
        firstname = "Martin",
        lastname = "Kuzdowicz",
        email = "martin.kuzdowicz@gmail.com",
        salutation = "Mr",
        telephone = "+44 7731 671016",
        `type` = "barrister",
        orgName = "Advice UK",
        address = Address(postcode = "E14 9EP")
      )
    )

    log.info(s"usr1UUID => $usr1UUID")

  }

}
