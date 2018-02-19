package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.{OrganisationsRepoInMemoImpl, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import org.scalatest.{Matchers, OneInstancePerTest, WordSpec}

trait UserProfilesServiceRouterSpecTrait extends WordSpec
  with Matchers
  with ScalatestRouteTest
  with OneInstancePerTest
  with UserProfilesServiceRouterJsonSupport {

  implicit val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  val usrProfServiceCtrl = UserProfilesServiceRouter.apply
  val usrProfServiceCtrlRouter = usrProfServiceCtrl.route

  val mainEndpoint = usrProfServiceCtrl.mainEndpoint
  val usersPath = s"/$mainEndpoint/${usrProfServiceCtrl.usersEndpoint}"

}
