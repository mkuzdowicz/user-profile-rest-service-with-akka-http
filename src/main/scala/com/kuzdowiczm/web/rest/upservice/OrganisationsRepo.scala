package com.kuzdowiczm.web.rest.upservice

import java.util.UUID

object OrganisationsRepo {

  def add(createOrgReq: CreateOrgReq): String = {
    val orgUUID = UUID.randomUUID().toString
    DB.organisations += createOrgReq.name -> Organisation(
      id = orgUUID,
      name = createOrgReq.name,
      email = createOrgReq.email,
      `type` = createOrgReq.`type`,
      address = createOrgReq.address
    )
    orgUUID
  }

  def findOneBy(name: String): Option[Organisation] = {
    DB.organisations.get(name)
  }

}
