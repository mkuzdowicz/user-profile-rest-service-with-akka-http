package com.kuzdowiczm.web.rest.upservice

object Application extends App {

  println("Hello")

  val usr1UUID = UserProfilesService.add(
    CreateUserReq(
      firstname = "Martin",
      lastname = "Kuzdowicz",
      email = "martin.kuzdowicz@gmail.com",
      salutation = "Mr",
      telephone = "+44 7731 671016",
      `type` = "barrister"
    )
  )

  val usr1 = UserProfilesService.findBy(usr1UUID).get

  println(usr1)

}
