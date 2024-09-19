package com.example.newsletterwithspring.user

import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userRepository: UserRepository,
    private val userService: UserService
) {
    @GetMapping("/user")
    fun getUser(): List<User> {
        return userRepository.findAll()
    }

    @PostMapping("/user")
    fun addUser(@RequestBody request: CreateUserRequest): User {
        val user = userService.addUser(request)
        return user
    }

    @PutMapping("/user/{id}")
    fun updateUser(@RequestBody request: CreateUserRequest, @PathVariable id: Long): User {
        val user = userService.updateUser(id, request)
        return user
    }
}
