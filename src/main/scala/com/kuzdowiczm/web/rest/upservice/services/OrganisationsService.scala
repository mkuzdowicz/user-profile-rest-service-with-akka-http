package com.kuzdowiczm.web.rest.upservice.services

import com.kuzdowiczm.web.rest.upservice.domain.{CreateOrgReq, Organisation}
import com.kuzdowiczm.web.rest.upservice.repositories.OrganisationsRepo

object OrganisationsService {
  def apply(implicit orgsRepo: OrganisationsRepo): OrganisationsService = {
    new OrganisationsService()
  }
}

class OrganisationsService(implicit private val orgsRepo: OrganisationsRepo) {

  def create(createOrgReq: CreateOrgReq): Option[Organisation] = {
    orgsRepo.create(createOrgReq)
  }

  def findOneBy(id: String): Organisation = {
    orgsRepo.findOneBy(id).orNull
  }

}
