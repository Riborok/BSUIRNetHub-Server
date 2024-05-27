package com.bsuirnethub.entity

import com.bsuirnethub.alias.GroupId
import jakarta.persistence.*

@Entity
@Table(
    name = "user_groups",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_group_user",
            columnNames = ["group_id", "user_id"]
        )
    ]
)
class GroupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "group_id")
    var groupId: GroupId? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
)
