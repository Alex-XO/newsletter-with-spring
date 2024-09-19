package com.example.newsletterwithspring.category

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Categories, Long> {
    fun findByName(name: String): Categories?
}
