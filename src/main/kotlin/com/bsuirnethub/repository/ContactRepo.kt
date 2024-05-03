package com.bsuirnethub.repository

import com.bsuirnethub.entity.ContactEntity
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContactRepo: JpaRepository<ContactEntity, Long> {
    fun findByUserAndContactId(user: UserEntity, contactId: String): ContactEntity?
}
