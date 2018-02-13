package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.db.OrganisationsRepoInMemoImpl

object Application extends App {

  OrganisationsRepoInMemoImpl.add(CreateOrgReq(
    name = "Advice UK",
    email = "test@email",
    `type` = "ADVICE_SERVICE",
    address = Address(postcode = "EC2 67")
  ))

  val usr1UUID = UserProfilesService.add(
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

  val usr1 = UserProfilesService.findBy(usr1UUID)

  println(usr1)

}
