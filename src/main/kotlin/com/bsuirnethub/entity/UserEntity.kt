package com.bsuirnethub.entity

import jakarta.persistence.*

@Entity
@Table(name = "users", indexes = [Index(name = "idx_user_id", columnList = "user_id")])
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id")
    var userId: String? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var contacts: List<ContactEntity> = ArrayList()
)
