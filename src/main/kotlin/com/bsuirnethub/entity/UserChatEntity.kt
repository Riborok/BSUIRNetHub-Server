package com.bsuirnethub.entity

import jakarta.persistence.*

@Entity
@Table(name = "user_chats",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_chat_user",
            columnNames = ["chat_id", "user_id"]
        )
    ],
    indexes = [
    Index(name = "idx_user_id", columnList = "user_id"),
    Index(name = "idx_chat_id", columnList = "chat_id")
])
class UserChatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "chat_id")
    var chat: ChatEntity? = null,

    @Column(name = "unread_messages")
    private var _unreadMessages: Int? = 0
) {
    var unreadMessages: Int
        get() = _unreadMessages ?: 0
        set(value) { _unreadMessages = value }
}
