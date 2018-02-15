package com.kuzdowiczm.web.rest.upservice.repositories

import com.kuzdowiczm.web.rest.upservice.domain.{CreateOrgReq, Organisation}

trait OrganisationsRepo {

  def create(createOrgReq: CreateOrgReq): Option[Organisation]

  def findOneBy(name: String): Option[Organisation]

}
