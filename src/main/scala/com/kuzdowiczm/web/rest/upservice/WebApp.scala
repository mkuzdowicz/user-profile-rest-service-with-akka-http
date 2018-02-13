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

  DataInitialiser.init(usrProfilesService, orgsService)

  val usrProfServiceCtrlRouter = new UsrProfilesServiceCtrl().route

  Http().bindAndHandle(handler = usrProfServiceCtrlRouter, interface = "localhost", port = 8080) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}")
  } recover { case ex =>
    ex.printStackTrace()
  }

}
