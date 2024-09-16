package com.example.newsletterwithspring.articles

import jakarta.persistence.*
import java.time.OffsetDateTime


@Entity
@Table(name = "articles")
data class Articles(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val author: String? = null,
    val title: String,
    val description: String,
    val url: String,
    val source: String,
    val image: String? = null,
    val category: String,
    val language: String,
    val country: String,
    val publishedAt: OffsetDateTime,
    val savedAt: OffsetDateTime = OffsetDateTime.now()
)
