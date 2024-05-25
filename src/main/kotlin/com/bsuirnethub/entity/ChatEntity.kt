package com.bsuirnethub.entity

import jakarta.persistence.*

@Entity
@Table(name = "chats")
class ChatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_chats",
        joinColumns = [JoinColumn(name = "chat_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var participants: MutableSet<UserEntity> = HashSet(),

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("timestamp DESC")
    var messages: MutableList<MessageEntity> = ArrayList()
)
