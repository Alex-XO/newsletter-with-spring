package com.example.newsletterwithspring.articles

import com.example.newsletterwithspring.category.Categories
import jakarta.persistence.*
import java.time.LocalDateTime

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
    val publishedAt: LocalDateTime,
    val savedAt: LocalDateTime = LocalDateTime.now(),
)
