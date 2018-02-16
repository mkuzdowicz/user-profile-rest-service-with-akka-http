package com.kuzdowiczm.web.rest.upservice.helpers

object ResponseMessagesHelper {

  def ifNoUserProfileFor(id: String) = s"there is no user profile with id: $id"

  def ifNewUserCreatedWith(id: String) = s"{msg: new user created with id: $id}"

  def ifUserUserUpdatedWith(id: String) = s"user with id: $id updated"

}
