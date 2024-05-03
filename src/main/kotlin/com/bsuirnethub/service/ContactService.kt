package com.bsuirnethub.service

import com.bsuirnethub.entity.ContactEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.ContactException
import com.bsuirnethub.model.Contact
import com.bsuirnethub.model.User
import com.bsuirnethub.repository.ContactRepo
import com.bsuirnethub.repository.UserRepo
import org.springframework.stereotype.Service

@Service
class ContactService(
    private val userRepository: UserRepo,
    private val contactRepository: ContactRepo
) {
    fun getAllContacts(userId: String): List<Contact> {
        val user = findUserOrThrow(userId)
        return User.toModel(user).contacts
    }

    private fun findUserOrThrow(userId: String): UserEntity {
        return userRepository.findByUserId(userId) ?: throw ContactException("User with id $userId not found")
    }

    fun addContact(userId: String, contactId: String): ContactEntity {
        val user = findUserOrThrow(userId)
        validateContactExists(contactId)
        validateContactIsNotUser(userId, contactId)
        validateContactNotExist(user, contactId)
        return saveNewContact(user, contactId)
    }

    private fun validateContactExists(contactId: String) {
        findUserOrThrow(contactId)
    }

    private fun validateContactIsNotUser(userId: String, contactId: String) {
        if (userId == contactId) {
            throw ContactException("Cannot add yourself as a contact")
        }
    }

    private fun validateContactNotExist(user: UserEntity, contactId: String) {
        val existingContact = contactRepository.findByUserAndContactId(user, contactId)
        if (existingContact != null) {
            throw ContactException("Contact with id $contactId already exists for user ${user.userId}")
        }
    }

    private fun saveNewContact(user: UserEntity, contactId: String): ContactEntity {
        val newContact = ContactEntity(contactId = contactId, user = user)
        return contactRepository.save(newContact)
    }

    fun deleteContact(userId: String, contactId: String) {
        val user = findUserOrThrow(userId)
        val contact = findContactOrThrow(user, contactId)
        deleteContact(contact)
    }

    private fun findContactOrThrow(user: UserEntity, contactId: String): ContactEntity {
        return contactRepository.findByUserAndContactId(user, contactId)
            ?: throw ContactException("Contact with id $contactId not found for user ${user.userId}")
    }

    private fun deleteContact(contact: ContactEntity) {
        contactRepository.delete(contact)
    }
}
