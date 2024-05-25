package com.bsuirnethub.entity

import jakarta.persistence.*

@Entity
@Table(name = "user_chats")
class UserChatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    var chat: ChatEntity? = null,

    @Column(name = "unread_messages")
    private var _unreadMessages: Int? = 0
) {
    var unreadMessages: Int
        get() = _unreadMessages ?: 0
        set(value) { _unreadMessages = value }
}
