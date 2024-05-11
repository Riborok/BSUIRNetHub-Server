package com.bsuirnethub.service

import com.bsuirnethub.entity.ContactEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.model.Contact
import com.bsuirnethub.model.User
import com.bsuirnethub.repository.ContactRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ContactService(
    private val userService: UserService,
    private val contactRepository: ContactRepository
) {
    fun getAllContacts(userId: String): List<Contact> {
        val user = userService.findUserOrThrow(userId)
        return User.toModel(user).contacts
    }

    fun deleteContact(userId: String, contactId: String) {
        val user = userService.findUserOrThrow(userId)
        val contact = findContactOrThrow(user, contactId)
        deleteContact(contact)
    }

    fun findContactOrThrow(user: UserEntity, contactId: String): ContactEntity {
        return contactRepository.findByUserAndContactId(user, contactId)
            ?: throw RestStatusException("Contact with id $contactId not found for user ${user.userId}", HttpStatus.NOT_FOUND)
    }

    private fun deleteContact(contact: ContactEntity) {
        contactRepository.delete(contact)
    }

    fun addContact(userId: String, contactId: String): ContactEntity {
        val user = userService.findUserOrThrow(userId)
        validateContactExists(contactId)
        validateContactIsNotUser(userId, contactId)
        validateContactNotExist(user, contactId)
        return saveNewContact(user, contactId)
    }

    private fun validateContactExists(contactId: String) {
        userService.findUserOrThrow(contactId)
    }

    private fun validateContactIsNotUser(userId: String, contactId: String) {
        if (userId == contactId) {
            throw RestStatusException("Cannot add yourself as a contact", HttpStatus.BAD_REQUEST)
        }
    }

    private fun validateContactNotExist(user: UserEntity, contactId: String) {
        val existingContact = contactRepository.findByUserAndContactId(user, contactId)
        if (existingContact != null) {
            throw RestStatusException("Contact with id $contactId already exists for user ${user.userId}", HttpStatus.BAD_REQUEST)
        }
    }

    private fun saveNewContact(user: UserEntity, contactId: String): ContactEntity {
        val newContact = ContactEntity(contactId = contactId, user = user)
        return contactRepository.save(newContact)
    }
}
