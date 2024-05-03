package com.bsuirnethub.model

import com.bsuirnethub.entity.ContactEntity

class Contact(
    var contactId: String? = null,
) {
    companion object {
        fun toModel(entity: ContactEntity): Contact {
            return Contact(entity.contactId)
        }
    }
}
