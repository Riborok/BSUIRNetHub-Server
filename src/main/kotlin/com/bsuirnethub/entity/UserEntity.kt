package com.bsuirnethub.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users", indexes = [Index(name = "idx_user_id", columnList = "user_id")])
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id")
    var userId: String? = null,

    @Column(name = "last_seen")
    var lastSeen: LocalDateTime? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var contacts: List<ContactEntity> = ArrayList()
)
