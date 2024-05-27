package com.bsuirnethub.entity

import com.bsuirnethub.alias.UserId
import jakarta.persistence.*

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_user_id",
            columnNames = ["user_id"]
        )
    ]
)
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id")
    var userId: UserId? = null,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var subscriptions: MutableList<SubscriptionEntity> = ArrayList(),

    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var subscribers: MutableList<SubscriptionEntity> = ArrayList(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var userChats: MutableList<UserChatEntity> = ArrayList(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var groups: MutableList<GroupEntity> = ArrayList(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var teachers: MutableList<TeacherEntity> = ArrayList()
)
