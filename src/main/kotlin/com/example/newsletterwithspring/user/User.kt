package com.example.newsletterwithspring.user

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val email: String,
    val fullName: String,
    val language: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_tags", // Таблица связей между users и tags
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    val tags: Set<Tag> = emptySet(),

    var lastSentAt: OffsetDateTime? = null
)

@Entity
@Table(name = "tags")
data class Tag(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String
)
