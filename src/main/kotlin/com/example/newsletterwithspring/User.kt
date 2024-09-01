package com.example.newsletterwithspring

data class User(
    val email: String,
    val fullName: String,
    val language: String,
    val tags: List<String>
)
