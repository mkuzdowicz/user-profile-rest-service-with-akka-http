package com.kuzdowiczm.web.rest.upservice

import com.kuzdowiczm.web.rest.upservice.repositories.inmemodb.InMemoDB

object InMemoDBTestUtils {

  def clearInMemoDB(inMemoDB: InMemoDB.type) =  this.synchronized {
    inMemoDB.userProfiles().keys.foreach(key => inMemoDB.userProfiles().remove(key))
    inMemoDB.organisations().keys.foreach(key => inMemoDB.organisations().remove(key))
  }

}
