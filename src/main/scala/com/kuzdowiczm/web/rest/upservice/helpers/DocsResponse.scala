package com.kuzdowiczm.web.rest.upservice.helpers

object DocsResponse {

  val SERVICE_LINKS_MAP = Map[String, String](
    "HTTP get: /users/<id>" -> "get single user",
    "HTTP delete: /users/<id>" -> "delete single user",
    "HTTP put: /users" -> "update single user (requires json payload)",
    "HTTP post: /users" -> "create single user (requires json payload)"
  )

}
