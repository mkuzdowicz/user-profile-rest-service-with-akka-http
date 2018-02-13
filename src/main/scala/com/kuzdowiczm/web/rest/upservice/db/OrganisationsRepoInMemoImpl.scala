package com.kuzdowiczm.web.rest.upservice.db

import java.util.UUID

import com.kuzdowiczm.web.rest.upservice.{CreateOrgReq, Organisation}

object OrganisationsRepoInMemoImpl extends OrganisationsRepo {

  def add(createOrgReq: CreateOrgReq): String = {
    val orgUUID = UUID.randomUUID().toString
    InMemoDB.organisations += createOrgReq.name -> Organisation(
      id = orgUUID,
      name = createOrgReq.name,
      email = createOrgReq.email,
      `type` = createOrgReq.`type`,
      address = createOrgReq.address
    )
    orgUUID
  }

  def findOneBy(name: String): Option[Organisation] = {
    InMemoDB.organisations.get(name)
  }

}
