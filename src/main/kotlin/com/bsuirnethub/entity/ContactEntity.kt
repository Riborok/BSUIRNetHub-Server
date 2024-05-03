package com.bsuirnethub.entity

import jakarta.persistence.*

@Entity
@Table(name = "contacts")
class ContactEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "contact_id")
    var contactId: String? = null,

    @ManyToOne
    @JoinColumn(name = "owner_id")
    var user: UserEntity? = null
)
