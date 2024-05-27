package com.bsuirnethub.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "user_subscriptions",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_user_subscription",
            columnNames = ["user_id", "subscription_id"]
        )
    ],
    indexes = [
        Index(name = "idx_user_id", columnList = "user_id"),
        Index(name = "idx_subscription_id", columnList = "subscription_id")
    ]
)
class SubscriptionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    var subscription: UserEntity? = null
)
