package com.bsuirnethub.entity

import jakarta.persistence.*

@Entity
@Table(name = "subscribers")
class SubscriberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "subscriber_id")
    var subscriberId: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    var user: UserEntity? = null
)
