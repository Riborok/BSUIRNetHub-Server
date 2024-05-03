package com.bsuirnethub.controller

import com.bsuirnethub.exception.ContactException
import com.bsuirnethub.model.Contact
import com.bsuirnethub.service.ContactService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/private/contacts")
class ContactController(private val contactService: ContactService) {
    @GetMapping
    fun getAllContacts(@AuthenticationPrincipal jwt: Jwt): ResponseEntity<List<Contact>> {
        val userId = jwt.subject
        val contacts = contactService.getAllContacts(userId)
        return ResponseEntity.ok(contacts)
    }

    @PutMapping("/{contactId}")
    fun addContact(@AuthenticationPrincipal jwt: Jwt, @PathVariable contactId: String): ResponseEntity<Any> {
        val userId = jwt.subject
        contactService.addContact(userId, contactId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{contactId}")
    fun deleteContact(@AuthenticationPrincipal jwt: Jwt, @PathVariable contactId: String): ResponseEntity<Any> {
        val userId = jwt.subject
        contactService.deleteContact(userId, contactId)
        return ResponseEntity.ok().build()
    }

    @ExceptionHandler(ContactException::class)
    fun handleException(ex: RuntimeException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }
}
