package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.repository.{OrganisationsRepo, OrganisationsRepoInMemoImpl, UserProfilesRepo, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.services.{OrganisationsService, UserProfilesService}

object Application extends App {

  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  private val usrProfilesService = UserProfilesService.apply
  private val orgsService = OrganisationsService.apply

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

  val usr1 = usrProfilesService.findBy(usr1UUID)

  println(usr1)

}
