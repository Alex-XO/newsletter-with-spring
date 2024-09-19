package com.example.newsletterwithspring.user

import com.example.newsletterwithspring.category.Categories
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var email: String,
    var fullName: String,
    var language: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_category",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var categories: Set<Categories> = emptySet(),

    var lastSentAt: OffsetDateTime? = null
)

class CreateUserRequest(
    val email: String,
    val fullName: String,
    val language: String,
    val category: Set<String>
)