package com.bsuirnethub.entity

import com.bsuirnethub.alias.UserId
import jakarta.persistence.*

@Entity
@Table(name = "users", indexes = [Index(name = "idx_user_id", columnList = "user_id")])
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id", unique = true)
    var userId: UserId? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_subscriptions",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "subscription_id")]
    )
    var subscriptions: MutableSet<UserEntity> = HashSet(),

    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.LAZY)
    var subscribers: MutableSet<UserEntity> = HashSet()
)
