package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.db.OrganisationsRepo

object OrganisationsService {
  def apply(implicit orgsRepo: OrganisationsRepo): OrganisationsService = {
    new OrganisationsService()
  }
}

class OrganisationsService(implicit private val orgsRepo: OrganisationsRepo) {

  def add(createOrgReq: CreateOrgReq): String = {
    orgsRepo.add(createOrgReq)
  }

  def findOneBy(name: String): Organisation = {
    orgsRepo.findOneBy(name).orNull
  }

}
