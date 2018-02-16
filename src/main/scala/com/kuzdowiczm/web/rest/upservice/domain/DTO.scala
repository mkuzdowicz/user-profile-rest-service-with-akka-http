package com.kuzdowiczm.web.rest.upservice.domain

sealed case class CreateOrgReq(
                                name: String,
                                email: String,
                                `type`: String,
                                address: Address
                              )

sealed case class CreateOrUpdateUserReq(
                                         id: Option[String],
                                         firstname: String,
                                         lastname: String,
                                         email: String,
                                         salutation: String,
                                         telephone: String,
                                         `type`: String,
                                         orgName: String,
                                         address: Address
                                       )
