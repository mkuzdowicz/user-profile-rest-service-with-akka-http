package com.kuzdowiczm.web.rest.upservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.kuzdowiczm.web.rest.upservice.repository.{OrganisationsRepo, OrganisationsRepoInMemoImpl, UserProfilesRepo, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.services.{OrganisationsService, UserProfilesService}

import scala.concurrent.duration._


object WebApp extends App {

  implicit val system = ActorSystem("user-profiles-service")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  private val usrProfilesService = UserProfilesService.apply
  private val orgsService = OrganisationsService.apply

  init(orgsService)

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

  def init(orgsService: OrganisationsService) = {
    orgsService.add(CreateOrgReq(
      name = "Advice UK",
      email = "test@email",
      `type` = "ADVICE_SERVICE",
      address = Address(postcode = "EC2 67")
    ))
  }

  val ctrl = new UsrProfilesServiceCtrl()

  Http().bindAndHandle(handler = ctrl.route, interface = "localhost", port = 8080) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}")
  } recover { case ex =>
  }

}
