package com.example.newsletterwithspring.category

import jakarta.persistence.*

@Entity
@Table(name = "category")
data class Categories(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String
)
