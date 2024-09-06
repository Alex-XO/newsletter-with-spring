package com.example.newsletterwithspring

import org.springframework.stereotype.Service

@Service
class UserService {

    private val users = listOf(
        User("my_mail@mail.ru", "Иван Иванов", "en", listOf("technology", "science", "sports", "politics")),
        User("user2@example.com", "John Doe", "en", listOf("sports", "politics"))
    )

    fun getUsers(): List<User> = users
}
