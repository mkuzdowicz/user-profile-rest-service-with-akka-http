package com.kuzdowiczm.web.rest.upservice.repositories.inmemodb

import java.util.UUID

import com.kuzdowiczm.web.rest.upservice.repositories.OrganisationsRepo
import com.kuzdowiczm.web.rest.upservice.{CreateOrgReq, Organisation}

object OrganisationsRepoInMemoImpl extends OrganisationsRepo {

  def create(createOrgReq: CreateOrgReq): Option[Organisation] = {
    val orgUUID = UUID.randomUUID().toString
    val newOrg = Organisation(
      id = orgUUID,
      name = createOrgReq.name,
      email = createOrgReq.email,
      `type` = createOrgReq.`type`,
      address = createOrgReq.address
    )
    InMemoDB.organisations += createOrgReq.name -> newOrg
    Option(newOrg)
  }

  def findOneBy(name: String): Option[Organisation] = {
    InMemoDB.organisations.get(name)
  }

}
