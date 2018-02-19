package com.kuzdowiczm.web.rest.upservice.repositories.inmemodb

import java.util.UUID

import com.kuzdowiczm.web.rest.upservice.domain.{CreateOrgReq, Organisation}
import com.kuzdowiczm.web.rest.upservice.repositories.OrganisationsRepo

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
    InMemoDB.organisations() += orgUUID -> newOrg
    Option(newOrg)
  }

  def findOneBy(id: String): Option[Organisation] = {
    InMemoDB.organisations().get(id)
  }

}
