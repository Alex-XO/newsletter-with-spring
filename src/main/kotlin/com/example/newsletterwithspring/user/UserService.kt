package com.example.newsletterwithspring.user

import com.example.newsletterwithspring.category.CategoryRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) {

    fun getUsers(): List<User> {
        return userRepository.findAll()
    }

    fun addUser(request: CreateUserRequest): User {
        val categories = request.category.map { name ->
            categoryRepository.findByName(name) ?: throw IllegalArgumentException("Category not found: $name")
        }.toSet()

        val user = User(
            email = request.email,
            fullName = request.fullName,
            language = request.language,
            categories = categories,
            lastSentAt = null
        )

        return userRepository.save(user)
    }

    fun updateUser(id: Long, request: CreateUserRequest): User {
        val user = userRepository.findById(id).orElseThrow { IllegalArgumentException("Invalid user Id: $id") }

        val categories = request.category.map { name ->
            categoryRepository.findByName(name) ?: throw IllegalArgumentException("Category not found: $name")
        }.toSet()

        user.email = request.email
        user.fullName = request.fullName
        user.language = request.language
        user.categories = categories

        return userRepository.save(user)
    }
}
