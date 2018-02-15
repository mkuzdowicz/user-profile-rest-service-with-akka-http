package com.kuzdowiczm.web.rest.upservice

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.directives.DebuggingDirectives
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

object Server {

  private val cfg = ConfigFactory.load()
  private val log = LoggerFactory.getLogger(getClass)
  private val appName = cfg.getString("app.service_name")

  // AKKA http web toolkit dependencies
  implicit val system = ActorSystem(appName)
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  def startServer(route: akka.http.scaladsl.server.Route, host: String, port: Int): Unit = {

    val routerWithLogging = DebuggingDirectives.
      logRequestResult("router with logging", Logging.InfoLevel)(route)

    Http().bindAndHandle(handler = routerWithLogging, interface = host, port = port) map { binding =>
      log.info(s"$appName running on ${binding.localAddress}")
    } recover { case ex =>
      ex.printStackTrace()
    }
  }
}