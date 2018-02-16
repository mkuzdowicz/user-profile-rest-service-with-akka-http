package com.kuzdowiczm.web.rest.upservice.repositories.inmemodb

import java.util.UUID

import com.kuzdowiczm.web.rest.upservice.domain.{CreateOrUpdateUserReq, Organisation, UserProfile}
import com.kuzdowiczm.web.rest.upservice.repositories.UserProfilesRepo

object UserProfilesRepoInMemoImpl extends UserProfilesRepo {

  def createOrUpdateFrom(createUserReq: CreateOrUpdateUserReq, org: Organisation): Option[UserProfile] = {
    createUserReq.id match {
      case Some(usrId) => {
        if (usrId.nonEmpty) update(createUserReq, org) else createNewFrom(createUserReq, org)
      }
      case None => createNewFrom(createUserReq, org)
    }
  }

  private def createNewFrom(createUserReq: CreateOrUpdateUserReq, org: Organisation): Option[UserProfile] = {
    val uuid = UUID.randomUUID().toString
    val newUserProfile = mapToUserProfile(uuid, createUserReq, org)
    InMemoDB.userProfiles += uuid -> newUserProfile

    Option(newUserProfile)
  }

  private def mapToUserProfile(uuid: String, createUserReq: CreateOrUpdateUserReq, org: Organisation): UserProfile = {
    val newUserProfile = UserProfile(
      id = uuid,
      firstname = createUserReq.firstname,
      lastname = createUserReq.lastname,
      email = createUserReq.email,
      salutation = createUserReq.salutation,
      telephone = createUserReq.telephone,
      `type` = createUserReq.`type`,
      organisation = org,
      address = createUserReq.address
    )
    newUserProfile
  }

  private def update(createUserReq: CreateOrUpdateUserReq, org: Organisation): Option[UserProfile] = {
    val usrId = createUserReq.id.get
    if (!InMemoDB.userProfiles.contains(usrId)) return None
    val updatedUserProfile = mapToUserProfile(usrId, createUserReq, org)
    InMemoDB.userProfiles += usrId -> updatedUserProfile
    Option(updatedUserProfile)
  }

  def findOneBy(id: String): Option[UserProfile] = {
    InMemoDB.userProfiles.get(id)
  }

  def deleteOneBy(id: String): Option[UserProfile] = {
    InMemoDB.userProfiles.remove(id)
  }

}
