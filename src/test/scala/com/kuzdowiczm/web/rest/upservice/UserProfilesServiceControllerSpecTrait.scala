package com.kuzdowiczm.web.rest.upservice

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.kuzdowiczm.web.rest.upservice.InMemoDBTestUtils.clearInMemoDB
import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.{InMemoDB, OrganisationsRepoInMemoImpl, UserProfilesRepoInMemoImpl}
import com.kuzdowiczm.web.rest.upservice.repositories.{OrganisationsRepo, UserProfilesRepo}
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}

trait UserProfilesServiceControllerSpecTrait extends WordSpec with Matchers with BeforeAndAfterEach with ScalatestRouteTest with UsrProfilesServiceCtrlJsonSupport {

  implicit val usrProfilesRepo: UserProfilesRepo = UserProfilesRepoInMemoImpl
  implicit val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  val usrProfServiceCtrl = new UsrProfilesServiceCtrl()
  val usrProfServiceCtrlRouter = usrProfServiceCtrl.route

  val mainEndpoint = usrProfServiceCtrl.mainEndpoint
  val usersPath = s"/$mainEndpoint/${usrProfServiceCtrl.usersEndpoint}"

  override def beforeEach(): Unit = {
    clearInMemoDB(InMemoDB)
  }
}
