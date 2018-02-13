package com.kuzdowiczm.web.rest.upservice.repository

import com.kuzdowiczm.web.rest.upservice.{CreateOrgReq, Organisation}

trait OrganisationsRepo {

  def add(createOrgReq: CreateOrgReq): String

  def findOneBy(name: String): Option[Organisation]

}
