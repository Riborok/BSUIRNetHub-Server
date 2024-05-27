package com.bsuirnethub.entity

import jakarta.persistence.*

@Entity
@Table(name = "chats")
class ChatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var userChats: MutableSet<UserChatEntity> = HashSet(),

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("timestamp DESC")
    var messages: MutableList<MessageEntity> = ArrayList()
)
