package com.bsuirnethub.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "messages", indexes = [Index(name = "idx_chat_id_timestamp", columnList = "chat_id, timestamp DESC")])
class MessageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    var chat: ChatEntity? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    var sender: UserEntity? = null,

    @Column(name = "content")
    var content: String? = null,

    @Column(name = "timestamp")
    var timestamp: LocalDateTime = LocalDateTime.now()
)
