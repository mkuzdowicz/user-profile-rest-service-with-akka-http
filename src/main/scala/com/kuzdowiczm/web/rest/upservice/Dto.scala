package com.kuzdowiczm.web.rest.upservice


sealed case class CreateOrgReq(name: String, email: String, `type`: String, address: Address)

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

sealed case class Response(userProfile: UserProfile)