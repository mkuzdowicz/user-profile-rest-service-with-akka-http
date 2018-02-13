package com.kuzdowiczm.web.rest.upservice


sealed case class Address(postcode: String)

sealed case class Organisation(
                                id: String,
                                name: String,
                                email: String,
                                `type`: String,
                                address: Address
                              )

sealed case class CreateOrgReq(name: String, email: String, `type`: String, address: Address)

sealed case class UserProfile(
                               id: String,
                               firstname: String,
                               lastname: String,
                               email: String,
                               salutation: String,
                               telephone: String,
                               `type`: String,
                               organisation: Organisation,
                               address: Address
                             )

sealed case class CreateUserReq(
                                 firstname: String,
                                 lastname: String,
                                 email: String,
                                 salutation: String,
                                 telephone: String,
                                 `type`: String,
                                 orgName: String,
                                 address: Address
                               )