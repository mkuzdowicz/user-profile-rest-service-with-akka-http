package com.kuzdowiczm.web.rest.upservice.domain

sealed case class Address(postcode: String)

sealed case class Organisation(
                                id: String,
                                name: String,
                                email: String,
                                `type`: String,
                                address: Address
                              )

sealed case class UserProfile(
                               id: String,
                               firstname: String,
                               lastname: String,
                               email: String,
                               salutation: String,
                               telephone: String,
                               `type`: String,
                               organisation: Option[Organisation],
                               address: Address
                             )
