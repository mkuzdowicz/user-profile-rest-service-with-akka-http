package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.db.{OrganisationsRepo, OrganisationsRepoInMemoImpl}

object OrganisationsService {

  private val orgsRepo: OrganisationsRepo = OrganisationsRepoInMemoImpl

  def add(createOrgReq: CreateOrgReq): String = {
    orgsRepo.add(createOrgReq)
  }

  def findOneBy(name: String): Organisation = {
    orgsRepo.findOneBy(name).orNull
  }

}
