package com.kuzdowiczm.web.rest.upservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.{OrganisationsRepoInMemoImpl, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.concurrent.duration._


object WebApp extends App {

  private val cfg = ConfigFactory.load()
  private val log = LoggerFactory.getLogger(getClass)
  private val appName = cfg.getString("app.service_name")

  // AKKA http web toolkit dependencies
  implicit val system = ActorSystem(appName)
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  // user-profiles-service dependencies
  implicit private val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  DataInitialiser.init

  val usrProfServiceCtrlRouter = new UsrProfilesServiceCtrl().route

  val host = cfg.getString("app.host")
  val port = cfg.getInt("app.port")

  Http().bindAndHandle(handler = usrProfServiceCtrlRouter, interface = host, port = port) map { binding =>
    log.info(s"$appName running on ${binding.localAddress}")
  } recover { case ex =>
    ex.printStackTrace()
  }

}
