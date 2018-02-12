package com.kuzdowiczm.web.rest.upservice

object Application extends App {

  println("Hello")

  OrganisationsRepo.add(CreateOrgReq(
    name = "Advice UK",
    email = "test@email",
    `type` = "ADVICE_SERVICE",
    address = Address(postcode = "")
  ))

  val usr1UUID = UserProfilesService.add(
    CreateUserReq(
      firstname = "Martin",
      lastname = "Kuzdowicz",
      email = "martin.kuzdowicz@gmail.com",
      salutation = "Mr",
      telephone = "+44 7731 671016",
      `type` = "barrister",
      orgName = "Advice UK"
    )
  )

  val usr1 = UserProfilesService.findBy(usr1UUID)

  println(usr1)

}
